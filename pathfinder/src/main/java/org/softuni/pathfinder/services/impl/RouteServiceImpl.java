package org.softuni.pathfinder.services.impl;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.softuni.pathfinder.domain.dtos.routes.RouteDto;
import org.softuni.pathfinder.domain.entities.Category;
import org.softuni.pathfinder.domain.entities.Route;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.domain.entities.enums.CategoryName;
import org.softuni.pathfinder.domain.entities.enums.Level;
import org.softuni.pathfinder.repositories.CategoryRepository;
import org.softuni.pathfinder.repositories.RouteRepository;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.RouteService;
import org.softuni.pathfinder.utils.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private LoggedInUser loggedInUser;
    private UserRepository userRepository;

    private CategoryRepository categoryRepository;

    @Value("${upload.directory}")
    private String uploadDir;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, LoggedInUser loggedInUser, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.routeRepository = routeRepository;
        this.loggedInUser = loggedInUser;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void registerRoute(RouteDto routeDto) throws IOException {
        Route route = new Route();
        User user = this.userRepository.findById(loggedInUser.getId()).orElse(null);

        Set<Category> collection = new HashSet<>();
        for (String category : routeDto.getCategories()) {
            Category catToAdd = this.categoryRepository.findByName(CategoryName.valueOf(category.toUpperCase()));
            collection.add(catToAdd);
        }
        String imageUrl = saveFile(routeDto.getImage());
        String gpxCoordinates = extractCoordinates(routeDto.getGpxCoordinates());

        route.setAuthor(user);
        route.setLevel(Level.valueOf(routeDto.getLevel()));
        route.setCategory(collection);
        route.setCategory(route.getCategory());
        route.setName(routeDto.getName());
        route.setImageUrl(imageUrl);
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
        return this.routeRepository.findById(id).orElse(null);
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
