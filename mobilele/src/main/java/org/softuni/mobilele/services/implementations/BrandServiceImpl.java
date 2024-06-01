package org.softuni.mobilele.services.implementations;

import org.softuni.mobilele.domain.entities.Brand;
import org.softuni.mobilele.repositories.BrandRepository;
import org.softuni.mobilele.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> getAllWithModels() {
        return this.brandRepository.findAllWithModels();
    }

    @Override
    public List<String> getAllBrandsNames() {
        return this.brandRepository.getNamesBrands();

    }
}
