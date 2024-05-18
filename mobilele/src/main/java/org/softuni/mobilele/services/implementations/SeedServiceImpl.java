package org.softuni.mobilele.services.implementations;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.softuni.mobilele.domain.dtos.brand.BrandDto;
import org.softuni.mobilele.domain.dtos.brand.BrandIdDto;
import org.softuni.mobilele.domain.dtos.model.ModelDto;
import org.softuni.mobilele.domain.dtos.model.ModelIdDto;
import org.softuni.mobilele.domain.dtos.offer.OfferDto;
import org.softuni.mobilele.domain.dtos.user.UserDto;
import org.softuni.mobilele.domain.dtos.user.UserIdDto;
import org.softuni.mobilele.domain.dtos.user_role.UserRoleDto;
import org.softuni.mobilele.domain.dtos.user_role.UserRoleIdDto;
import org.softuni.mobilele.domain.entities.*;
import org.softuni.mobilele.repositories.*;
import org.softuni.mobilele.services.SeedService;
import org.softuni.mobilele.utils.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

@Service
public class SeedServiceImpl implements SeedService {
    private ModelRepository modelRepository;
    private UserRepository userRepository;

    private OfferRepository offerRepository;
    private BrandRepository brandRepository;

    private UserRoleRepository userRoleRepository;

    private ModelMapper mapper;

    private Gson gson;

    private EntityManager entityManager;
    @Autowired
    public SeedServiceImpl(ModelRepository modelRepository, UserRepository userRepository, OfferRepository offerRepository, BrandRepository brandRepository, UserRoleRepository userRoleRepository, ModelMapper mapper, Gson gson, EntityManager entityManager) {
        this.modelRepository = modelRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.brandRepository = brandRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.entityManager = entityManager;
    }

    @Override
    public void seedBrands() throws FileNotFoundException {
        if(this.brandRepository.count()>0) return;

        FileReader fileReader = new FileReader(Paths.PATH_BRANDS);
        List<BrandDto> dtos = Arrays.stream(this.gson.fromJson(fileReader, BrandDto[].class))
                .toList();

        dtos.stream().map(brandDto -> {
            Brand mapped = this.mapper.map(brandDto, Brand.class);
            return mapped;
        }).forEach(this.brandRepository::saveAndFlush);
    }

    @Override
    public void seedModels() throws FileNotFoundException {
        if(this.modelRepository.count() > 0) return;

        FileReader fileReader = new FileReader(Paths.PATH_MODELS);
        List<ModelDto> modelDtos = Arrays.stream(this.gson.fromJson(fileReader, ModelDto[].class))
                .toList();

        modelDtos.stream().map(modelDto -> {
            BrandIdDto brandDto = modelDto.getBrand();

            Brand brand = this.brandRepository.findById(brandDto.getId()).orElseThrow();
            Model model = this.mapper.map(modelDto, Model.class);
            model.setBrand(brand);
            return model;
        }).forEach(this.modelRepository::saveAndFlush);

    }

    @Override
    public void seedOffers() throws FileNotFoundException {
        if(this.offerRepository.count() > 0) return;

        FileReader fileReader = new FileReader(Paths.PATH_OFFERS);
        List<OfferDto> offerDtos = Arrays.stream(this.gson.fromJson(fileReader, OfferDto[].class))
                .toList();

        offerDtos.stream()
                .map(offerDto -> {
                    ModelIdDto modelDto = offerDto.getModel();
                    UserIdDto sellerDto = offerDto.getSeller();

                    Model model = this.modelRepository.findById(modelDto.getId()).orElseThrow();
                    User user = this.userRepository.findById(sellerDto.getId()).orElseThrow();

                    Offer offer = this.mapper.map(offerDto, Offer.class);
                    offer.setModel(model);
                    offer.setSeller(user);

                    return offer;
                }).forEach(this.offerRepository::saveAndFlush);
    }

    @Override
    @Transactional
    public void seedUsers() throws FileNotFoundException {
        if (this.userRepository.count() > 0) {
            return;
        }

        FileReader fileReader = new FileReader(Paths.PATH_USERS);
        List<UserDto> userDtos = Arrays.stream(this.gson.fromJson(fileReader, UserDto[].class))
                .toList();

        userDtos.stream().map(userDto -> {
            UserRoleIdDto roleDto = userDto.getRole();
            UserRole userRole = this.userRoleRepository.findById(roleDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User role not found"));

            User user = this.mapper.map(userDto, User.class);
            user.setRole(userRole);

            return user;
        }).forEach(this.userRepository::saveAndFlush);
    }

    @Override
    public void seedUserRoles() throws FileNotFoundException {
        if(this.userRoleRepository.count()>0) return;
        FileReader fileReader = new FileReader(Paths.PATH_USER_ROLE);

        List<UserRoleDto> dtos = Arrays.stream(this.gson.fromJson(fileReader, UserRoleDto[].class))
                .toList();

        dtos.stream().map(userRoleDto -> this.mapper.map(userRoleDto, UserRole.class))
                .forEach(this.userRoleRepository::saveAndFlush);

    }
}
