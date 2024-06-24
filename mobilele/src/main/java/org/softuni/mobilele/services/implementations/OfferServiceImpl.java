package org.softuni.mobilele.services.implementations;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.softuni.mobilele.domain.dtos.offer.OfferDetailsDto;
import org.softuni.mobilele.domain.dtos.offer.OfferDisplayDto;
import org.softuni.mobilele.domain.dtos.offer.OfferRegisterDto;
import org.softuni.mobilele.domain.dtos.offer.OfferUpdateDto;
import org.softuni.mobilele.domain.entities.Brand;
import org.softuni.mobilele.domain.entities.Model;
import org.softuni.mobilele.domain.entities.Offer;
import org.softuni.mobilele.domain.entities.User;
import org.softuni.mobilele.domain.entities.enums.Category;
import org.softuni.mobilele.domain.entities.enums.Engine;
import org.softuni.mobilele.domain.entities.enums.Transmission;
import org.softuni.mobilele.exceptions.ObjectNotFoundException;
import org.softuni.mobilele.repositories.BrandRepository;
import org.softuni.mobilele.repositories.ModelRepository;
import org.softuni.mobilele.repositories.OfferRepository;
import org.softuni.mobilele.repositories.UserRepository;
import org.softuni.mobilele.services.OfferService;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository;
    private BrandRepository brandRepository;
    private ModelRepository modelRepository;

    private UserRepository userRepository;
    private ModelMapper mapper;

    @Value("${upload.directory}")
    private String uploadDir;
    private UserServiceImpl userService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, BrandRepository brandRepository, ModelRepository modelRepository, UserRepository userRepository, ModelMapper mapper, UserServiceImpl userService) {
        this.offerRepository = offerRepository;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public List<OfferDisplayDto> getAllOffers() {
         return this.offerRepository.findAll().stream().map(offer -> this.mapper.map(offer,OfferDisplayDto.class))
                 .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addOffer(OfferRegisterDto offerRegisterDto) throws IOException {
        String brandName = offerRegisterDto.getBrand();
        String photoUrl = saveFile(offerRegisterDto.getPhoto());
        Category category = offerRegisterDto.getCategory();
        Engine engine = offerRegisterDto.getEngine();
        String name = offerRegisterDto.getName();
        Long mileage = offerRegisterDto.getMileage();
        String description = offerRegisterDto.getDescription();
        BigInteger price = offerRegisterDto.getPrice();
        Transmission transmission = offerRegisterDto.getTransmission();
        Year year = offerRegisterDto.getYear();

        Brand brand = this.brandRepository.findByName(brandName).orElseThrow();

        Model model = createModel(name, brand, category, photoUrl);
        this.modelRepository.saveAndFlush(model);


        Offer currentOffer = createOffer(model, engine, mileage, price, year, transmission, description);

        this.offerRepository.saveAndFlush(currentOffer);

    }

    @Override
    public OfferDetailsDto getOfferById(Long id) {
        Offer offer = this.offerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No such offer",id));
        return this.mapper.map(offer, OfferDetailsDto.class);
    }
    @Override
    @Transactional
    public void deleteOffer(Long offerId) {
        if(!this.userService.isLoggedCreator(offerId)){
            throw new IllegalArgumentException("You are not allowed to do this kind of operations !");
        }
        Offer offerToDelete = this.offerRepository.findById(offerId).orElseThrow();

        Model modelOfTheOffer = offerToDelete.getModel();
        this.offerRepository.deleteOffersByModelId(offerToDelete.getId());
        // Check if the model has any other offers
        long offerCount = this.offerRepository.countByModelId(modelOfTheOffer.getId());
        if (offerCount == 0) {
            this.modelRepository.deleteModelById(modelOfTheOffer.getId());
        }
    }

    @Override
    public void updateOffer(Long offerId, OfferUpdateDto offerUpdateDto) throws IOException {
        if(!this.userService.isLoggedCreator(offerId)){
            throw new IllegalArgumentException("You are not allowed to do this kind of operations !");
        }
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(()-> new ObjectNotFoundException("No such offer",offerId));
        String brandName = offerUpdateDto.getBrand();
        String photoUrl = saveFile(offerUpdateDto.getPhoto());
        Category category = offerUpdateDto.getCategory();
        Engine engine = offerUpdateDto.getEngine();
        String name = offerUpdateDto.getName();
        Long mileage = offerUpdateDto.getMileage();
        String description = offerUpdateDto.getDescription();
        BigInteger price = offerUpdateDto.getPrice();
        Transmission transmission = offerUpdateDto.getTransmission();
        Year year = offerUpdateDto.getYear();

        Brand brand = brandName.isBlank() ? null : this.brandRepository.findByName(brandName).orElseThrow();;
        Model model = updateModel(name,brand,category,photoUrl,offer.getModel());
        this.modelRepository.saveAndFlush(model);

        updateFieldsOffer(offer,engine,mileage,description,price,transmission,year);

        this.offerRepository.saveAndFlush(offer);

    }

    private Model updateModel(String name, Brand brand, Category category, String photoUrl, Model existingModel) {
        if (existingModel == null) {
            // Handle the case where an existing model is not provided
            throw new IllegalArgumentException("Existing model cannot be null for update.");
        }
        existingModel.setModified(new Date());
        if (name != null && !name.isEmpty()) {
            existingModel.setName(name);
        }
        if (brand != null) {
            existingModel.setBrand(brand);
        }
        if (category != null) {
            existingModel.setCategory(category);
        }
        if (photoUrl != null && !photoUrl.isEmpty()) {
            existingModel.setImageUrl(photoUrl);
        }
        return existingModel;
    }

    private void updateFieldsOffer(Offer offer, Engine engine, Long mileage, String description, BigInteger price, Transmission transmission, Year year) {
        if (engine != null) {
            offer.setEngine(engine);
        }
        if (mileage != null) {
            offer.setMileage(mileage);
        }
        if (description != null && !description.isEmpty()) {
            offer.setDescription(description);
        }
        if (price != null) {
            offer.setPrice(price);
        }
        if (transmission != null) {
            offer.setTransmission(transmission);
        }
        if (year != null) {
            offer.setYear(year);
        }
        offer.setModified(new Date());
    }

    private Offer createOffer(Model model, Engine engine, Long mileage, BigInteger price, Year year, Transmission transmission, String description) {
        Offer currentOffer = new Offer();
        currentOffer.setModel(model);
        currentOffer.setEngine(engine);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User loggedUser = this.userRepository.getByUsername(currentUsername).orElseThrow();

        currentOffer.setSeller(loggedUser);
        currentOffer.setMileage(mileage);
        currentOffer.setPrice(price);
        currentOffer.setYear(year);
        currentOffer.setTransmission(transmission);
        currentOffer.setDescription(description);
        currentOffer.setCreated(new Date());
        return currentOffer;
    }

    private  Model createModel(String name, Brand brand, Category category, String photoUrl) {
        if(this.modelRepository.findByName(name).isPresent()){
            return this.modelRepository.findByName(name).get();
        }
        Model model = new Model();
        model.setCreated(new Date());
        model.setName(name);
        model.setBrand(brand);
        model.setCategory(category);
        model.setImageUrl(photoUrl);
        return model;
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // Generate a unique filename to avoid conflicts
        String filename = file.getOriginalFilename();

        // Construct the full path where you want to save the file
        Path filePath = Paths.get(uploadDir).resolve(filename);

        // Open an output stream to the newly created file
        try (OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE)) {
            // Copy the contents of the uploaded file to the output stream
            IOUtils.copy(file.getInputStream(), outputStream);
        }

        // Return the URL of the saved photo (relative to the application context)
        return "/images/" + filename;
    }
}
