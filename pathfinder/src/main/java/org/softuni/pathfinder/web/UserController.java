package org.softuni.pathfinder.web;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.softuni.pathfinder.domain.dtos.UserLogInDto;
import org.softuni.pathfinder.domain.dtos.UserRegisterDto;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.UserService;
import org.softuni.pathfinder.utils.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private UserService userService;
    private ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/users/register")
    public String showRegistrationForm(Model model) {
        // Create a new UserRegisterDto object and add it to the model
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "register";
    }

    @PostMapping("/users/register")
    public ModelAndView registerConfirm(@Valid UserRegisterDto userRegisterDto,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        // Check if password matches confirm password
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            bindingResult.rejectValue("password", "error.user", "Passwords do not match.");
        }

        // Check if user with the same email exists
        if (userService.usernameByEmailExists(userRegisterDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "User with this email already exists.");
        }

        // Check if user with the same username exists
        if (userService.userByUsernameExists(userRegisterDto.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "User with this username already exists.");
        }

        // If there are any validation errors, return to the registration page with error messages
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else {
            // Otherwise, proceed with user registration
            // Your registration logic here
            modelAndView.setViewName("redirect:/users/login");
            this.userService.register(userRegisterDto);
            // Redirect to login page after successful registration
        }

        return modelAndView;
    }

    @GetMapping("/users/login")
    public ModelAndView showLoginForm() {
        // Create a new ModelAndView object
        ModelAndView modelAndView = new ModelAndView();

        // Add userLogInDto object to the ModelAndView
        modelAndView.addObject("userLogInDto", new UserLogInDto());

        // Set the view name to "login", assuming "login" is the name of your Thymeleaf template
        modelAndView.setViewName("login");

        return modelAndView;
    }
    @PostMapping("/users/login")
    public ModelAndView logInUser(@Valid UserLogInDto userLogInDto,
                                  BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();

        // Check if username exists
        if (!this.userService.userByUsernameExists(userLogInDto.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Wrong username. Please try again.");
        }

        // Check if password is correct for the given username
        if (!userService.checkPasswordCorrectForTheUsername(userLogInDto)) {
            bindingResult.rejectValue("password", "error.user", "Wrong password. Please try again.");
        }

        // If there are errors, return to the login page with error messages
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("login");
            modelAndView.addObject("userLogInDto", userLogInDto);
            modelAndView.addObject("bindingResult", bindingResult);
        } else {
            // If no errors, perform login and redirect to home page
            this.userService.login(userLogInDto);
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @GetMapping("/users/profile")
    public  ModelAndView profilePicture(){
        ModelAndView modelAndView = new ModelAndView("profile");

        boolean loggedIn = this.userService.isLoggedIn();
        modelAndView.addObject("loggedIn", loggedIn);


        boolean isAdmin = this.userService.isAdmin();
        modelAndView.addObject("isAdmin", isAdmin);
        // Retrieve the currently logged-in user
        LoggedInUser loggedUser = userService.getLoggedInUser();

        modelAndView.addObject("loggedIn", loggedUser);

        return modelAndView;
    }



}
