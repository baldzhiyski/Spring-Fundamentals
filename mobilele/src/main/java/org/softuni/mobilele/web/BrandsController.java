package org.softuni.mobilele.web;

import org.softuni.mobilele.domain.entities.Brand;
import org.softuni.mobilele.services.BrandService;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BrandsController {

    private BrandService brandService;
    private UserService userService;

    @Autowired
    public BrandsController(BrandService brandService, UserService userService) {
        this.brandService = brandService;
        this.userService = userService;
    }

    @GetMapping("/brands/all")
    public String getAllBrands(Model model) {

        List<Brand> brands = brandService.getAllWithModels();
        model.addAttribute("brands", brands);


        return "brands";
    }
}
