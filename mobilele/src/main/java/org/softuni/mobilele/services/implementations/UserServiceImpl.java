package org.softuni.mobilele.services.implementations;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.softuni.mobilele.domain.dtos.user.UserLogInDto;
import org.softuni.mobilele.domain.dtos.user.UserRegisterDto;
import org.softuni.mobilele.domain.entities.Offer;
import org.softuni.mobilele.domain.entities.User;
import org.softuni.mobilele.domain.entities.UserRole;
import org.softuni.mobilele.repositories.OfferRepository;
import org.softuni.mobilele.repositories.UserRepository;
import org.softuni.mobilele.repositories.UserRoleRepository;
import org.softuni.mobilele.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.Date;

@Service
public class UserServiceImpl  implements UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;
    private UserRoleRepository userRoleRepository;

    @Value("${upload.directory}")
    private String uploadDir;

    private OfferRepository offerRepository;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, UserRoleRepository userRoleRepository, OfferRepository offerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userRoleRepository = userRoleRepository;
        this.offerRepository = offerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) throws IOException {
        UserRole userRole = userRoleRepository.findById(userRegisterDto.getRole()).orElseThrow();
        User user = this.mapper.map(userRegisterDto, User.class);
        String photoUrl = saveFile(userRegisterDto.getPhoto());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setImageUrl(photoUrl);
        user.setRole(userRole);
        user.setCreated(new Date());
        user.setActive(true);

        this.userRepository.saveAndFlush(user);
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
    @Override
    public boolean userByUsernameExists(String username) {
        return this.userRepository.getByUsername(username).isPresent();
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getByUsername(username).orElse(null);
    }


    @Override
    public boolean isLoggedCreator(Long offerId) {
        Offer offerById = offerRepository.findById(offerId).orElseThrow();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return offerById.getSeller().getUsername().equals(currentUsername);
    }


}
