package org.softuni.pathfinder.services.impl;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.softuni.pathfinder.domain.dtos.routes.RouteCategoryViewModel;
import org.softuni.pathfinder.domain.dtos.routes.RouteDto;
import org.softuni.pathfinder.domain.entities.Category;
import org.softuni.pathfinder.domain.entities.Picture;
import org.softuni.pathfinder.domain.entities.Route;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.domain.entities.enums.CategoryName;
import org.softuni.pathfinder.repositories.CategoryRepository;
import org.softuni.pathfinder.repositories.PictureRepository;
import org.softuni.pathfinder.repositories.RouteRepository;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {
    private RouteRepository routeRepository;
    private UserRepository userRepository;

    private PictureRepository pictureRepository;

    private CategoryRepository categoryRepository;

    private ModelMapper mapper;

    @Value("${upload.directory}")
    private String uploadDir;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository,UserRepository userRepository, PictureRepository pictureRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void registerRoute(RouteDto routeDto) throws IOException {
        Route route = new Route();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = this.userRepository.getUserByUsername(currentUsername).orElse(null);

        Set<Category> collection = new HashSet<>();
        for (CategoryName categoryName : routeDto.getCategories()) {
            Category catToAdd = this.categoryRepository.findByName(categoryName);
            collection.add(catToAdd);
        }

        List<MultipartFile> images = routeDto.getImages();
        Set<Picture> pictures = new HashSet<>();

        for (MultipartFile image : images) {
            StringBuilder nameOfPic = new StringBuilder();
            String imageUrl = saveFile(image,nameOfPic);
            Picture picture = new Picture();
            picture.setUrl(imageUrl);
            picture.setRoute(route);
            picture.setAuthor(user);
            picture.setTitle(nameOfPic.toString());

            pictures.add(picture);
            this.pictureRepository.saveAndFlush(picture);
        }

        String gpxCoordinates = extractCoordinates(routeDto.getGpxCoordinates());

        route.setAuthor(user);
        route.setLevel(routeDto.getLevel());
        route.setCategory(collection);
        route.setCategory(route.getCategory());
        route.setName(routeDto.getName());
        route.setPictures(pictures);
        route.setVideoUrl(routeDto.getVideoUrl());
        route.setGpxCoordinates(gpxCoordinates);
        route.setDescription(routeDto.getDescription());

        this.routeRepository.saveAndFlush(route);
    }

    @Override
    public List<Route> getAllRoutes() {
        return this.routeRepository.findAll();
    }

    @Override
    public Route findById(Long id) {
        return this.routeRepository.findById(id).orElseThrow();
    }

    @Override
    public List<RouteCategoryViewModel> getAllByCategory(CategoryName categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        List<Route> allByCategory = this.routeRepository.getAllByCategory(category);

        return allByCategory.stream()
                .map(cat -> this.mapper.map(cat, RouteCategoryViewModel.class))
                .collect(Collectors.toList());


    }

    @Override
    public Route mapToRoute(RouteCategoryViewModel routeCategoryViewModel) {
        return this.mapper.map(routeCategoryViewModel,Route.class);
    }

    private String saveFile(MultipartFile file, StringBuilder nameOfPic) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // Generate a unique filename to avoid conflicts
        String filename = file.getOriginalFilename();
        nameOfPic.append(filename);

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

    private String extractCoordinates(MultipartFile gpxCoordinates) throws IOException {
        if (gpxCoordinates == null || gpxCoordinates.isEmpty()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(gpxCoordinates.getInputStream(), StandardCharsets.UTF_8))) {
            // Read the contents of the GPX file and convert it to plain text
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
