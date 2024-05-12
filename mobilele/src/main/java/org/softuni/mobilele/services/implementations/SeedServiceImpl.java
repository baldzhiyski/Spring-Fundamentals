package org.softuni.mobilele.services.implementations;

import org.modelmapper.ModelMapper;
import org.softuni.mobilele.repositories.*;
import org.softuni.mobilele.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeedServiceImpl implements SeedService {
    private ModelRepository modelRepository;
    private UserRepository userRepository;

    private OfferRepository offerRepository;
    private BrandRepository brandRepository;

    private UserRoleRepository userRoleRepository;

    private ModelMapper mapper;

    @Autowired
    public SeedServiceImpl(ModelRepository modelRepository, UserRepository userRepository, OfferRepository offerRepository, BrandRepository brandRepository, UserRoleRepository userRoleRepository, ModelMapper mapper) {
        this.modelRepository = modelRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.brandRepository = brandRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedBrands() {

    }

    @Override
    public void seedModels() {

    }

    @Override
    public void seedOffers() {

    }

    @Override
    public void seedUsers() {

    }

    @Override
    public void seedUserRoles() {

    }
}
