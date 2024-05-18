package org.softuni.mobilele.services.implementations;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.softuni.mobilele.domain.dtos.offer.OfferRegisterDto;
import org.softuni.mobilele.domain.entities.Brand;
import org.softuni.mobilele.domain.entities.Model;
import org.softuni.mobilele.domain.entities.Offer;
import org.softuni.mobilele.domain.entities.enums.Category;
import org.softuni.mobilele.domain.entities.enums.Engine;
import org.softuni.mobilele.domain.entities.enums.Transmission;
import org.softuni.mobilele.repositories.BrandRepository;
import org.softuni.mobilele.repositories.ModelRepository;
import org.softuni.mobilele.repositories.OfferRepository;
import org.softuni.mobilele.services.OfferService;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository;
    private BrandRepository brandRepository;
    private ModelRepository modelRepository;

    private UserService userService;

    @Value("${upload.directory}")
    private String uploadDir;
    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, BrandRepository brandRepository, ModelRepository modelRepository, UserService userService) {
        this.offerRepository = offerRepository;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.userService = userService;
    }

    @Override
    public List<Offer> getAllOffers() {
        return this.offerRepository.findAll();
    }

    @Override
    @Transactional
    public void addOffer(OfferRegisterDto offerRegisterDto) throws IOException {
        String brandName = offerRegisterDto.getBrand();
        String photoUrl = saveFile(offerRegisterDto.getPhoto());
        String category = offerRegisterDto.getCategory();
        String engine = offerRegisterDto.getEngine();
        String name = offerRegisterDto.getName();
        Long mileage = offerRegisterDto.getMileage();
        String description = offerRegisterDto.getDescription();
        Long price = offerRegisterDto.getPrice();
        String transmission = offerRegisterDto.getTransmission();
        Year year = offerRegisterDto.getYear();

        Brand brand = this.brandRepository.findByName(brandName).orElseThrow();

        Model model = new Model();
        model.setCreated(new Date());
        model.setName(name);
        model.setBrand(brand);
        model.setCategory(Category.valueOf(category));
        model.setImageUrl(photoUrl);
        this.modelRepository.saveAndFlush(model);


        Offer currentOffer = new Offer();
        currentOffer.setModel(model);
        currentOffer.setEngine(Engine.valueOf(engine));
        currentOffer.setSeller(userService.getLoggedUser());
        currentOffer.setMileage(mileage);
        currentOffer.setPrice(Double.valueOf(price));
        currentOffer.setYear(year);
        currentOffer.setTransmission(Transmission.valueOf(transmission));
        currentOffer.setDescription(description);
        currentOffer.setCreated(new Date());

        this.offerRepository.saveAndFlush(currentOffer);

    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // Generate a unique filename to avoid conflicts
        String filename = file.getOriginalFilename();

        // Construct the full path where you want to save the file
        Path filePath = Paths.get(uploadDir).resolve(filename);

        // Create the directory if it doesn't exist
        Files.createDirectories(filePath.getParent());

        // Open an output stream to the newly created file
        try (OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE)) {
            // Copy the contents of the uploaded file to the output stream
            IOUtils.copy(file.getInputStream(), outputStream);
        }

        // Return the URL of the saved photo (relative to the application context)
        return "/img/" + filename;
    }
}
