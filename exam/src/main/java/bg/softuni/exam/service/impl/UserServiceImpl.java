package bg.softuni.exam.service.impl;

import bg.softuni.exam.model.dto.user.LogInDto;
import bg.softuni.exam.model.dto.user.RegisterDto;
import bg.softuni.exam.model.entity.Painting;
import bg.softuni.exam.model.entity.User;
import bg.softuni.exam.repository.PaintingRepository;
import bg.softuni.exam.repository.UserRepository;
import bg.softuni.exam.service.UserService;
import bg.softuni.exam.util.CurrentLoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private CurrentLoggedUser currentLoggedUser;
    private UserRepository userRepository;
    private ModelMapper mapper;
    private PasswordEncoder passwordEncoder;

    private PaintingRepository paintingRepository;

    public UserServiceImpl(CurrentLoggedUser currentLoggedUser, UserRepository userRepository, ModelMapper mapper, PasswordEncoder passwordEncoder, PaintingRepository paintingRepository) {
        this.currentLoggedUser = currentLoggedUser;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.paintingRepository = paintingRepository;
    }

    @Override
    public CurrentLoggedUser getLoggedUser() {
        return currentLoggedUser;
    }

    @Override
    public boolean userByUsernameExists(String username) {
        return this.userRepository.findByUsername(username).isPresent();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public boolean usernameByEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void logIn(LogInDto logInDto) {
        User user = userRepository.findByUsername(logInDto.getUsername()).orElseThrow();
        this.currentLoggedUser.setLogged(true);
        this.currentLoggedUser.setId(user.getId());
        this.currentLoggedUser.setUsername(user.getUsername());
    }

    @Override
    public void registerUser(RegisterDto userRegisterDto) {
        User userToBeAdded = this.mapper.map(userRegisterDto, User.class);
        userToBeAdded.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        this.userRepository.saveAndFlush(userToBeAdded);
    }

    @Override
    public void logOutCurrentUser() {
        this.currentLoggedUser.setLogged(false);
        this.currentLoggedUser.setUsername("");
    }

    @Override
    public User getUserFromLogged() {
        return this.userRepository.findByUsername(currentLoggedUser.getUsername()).orElseThrow();
    }

    @Override
    public void addToFavourites(UUID paintingId) {
        User user = this.userRepository.findByUsername(currentLoggedUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + currentLoggedUser.getUsername()));

        Painting painting = this.paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found with id: " + paintingId));

        // Check if the painting is already in the user's favorites (optional step)
        if (user.getFavouritePaintings().contains(painting)) {
            throw new IllegalArgumentException("Painting is already in favorites for user: " + currentLoggedUser.getUsername());
        }

        // Add painting to user's favorites
        painting.setFavourite(true); // You might set this flag based on your business logic
        user.getFavouritePaintings().add(painting);

        this.paintingRepository.saveAndFlush(painting);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void removePainting(UUID paintingId) {
        Painting painting = paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting with id " + paintingId + " not found"));

        User owner = userRepository.findByUsername(currentLoggedUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));

        if (!painting.isFavourite()) {
            // No other user has favorited it, so we can delete
            List<User> users= new ArrayList<>();
            for (User user : userRepository.findAll()) {
                user.getRatedPaintings().remove(painting);
                users.add(user);
            }
            owner.getCreatedPaintings().remove(painting);
            users.add(owner);
            userRepository.saveAllAndFlush(users);
            paintingRepository.delete(painting);
        } else {
            // Throw an exception or handle the case where deletion is not allowed
            throw new IllegalStateException("Cannot delete painting because it is favorited by other users");
        }
    }

    @Override
    @Transactional
    public void addVote(UUID paintingId) {
        User user = this.userRepository.findByUsername(currentLoggedUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found")); // Handle user not found scenario

        Painting painting = this.paintingRepository.findById(paintingId)
                .orElseThrow(() -> new RuntimeException("Painting not found")); // Handle painting not found scenario

        if (!userHasRatedPainting(user, painting)) {
            painting.setVotes(painting.getVotes() + 1);
            user.getRatedPaintings().add(painting);

            this.paintingRepository.saveAndFlush(painting);
            this.userRepository.saveAndFlush(user);
        }
    }

    private boolean userHasRatedPainting(User user, Painting painting) {
        for (Painting ratedPainting : user.getRatedPaintings()) {
            if (ratedPainting.getId().equals(painting.getId())) {
                return true;
            }
        }
        return false;
    }
}
