package org.softuni.pathfinder.web;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.softuni.pathfinder.domain.dtos.user.UserLogInDto;
import org.softuni.pathfinder.domain.dtos.user.UserRegisterDto;
import org.softuni.pathfinder.services.UserService;
import org.softuni.pathfinder.utils.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";
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
        if(!model.containsAttribute("userRegisterDto")) {
            model.addAttribute("userRegisterDto", new UserRegisterDto());
        }
        return "register";
    }

    @PostMapping("/users/register")
    public ModelAndView register(@Valid UserRegisterDto userRegisterDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        final ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            final String attributeName = "userRegisterDto";
            redirectAttributes
                    .addFlashAttribute(attributeName, userRegisterDto)
                    .addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            modelAndView.setViewName("redirect:/users/register");

        } else {
            this.userService.register(userRegisterDto);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully registered ! Please Log In !");
            modelAndView.setViewName("redirect:login");
        }

        return modelAndView;
    }

    @GetMapping("/users/login")
    public ModelAndView showLoginForm(Model model) {
        // Create a new ModelAndView object
        ModelAndView modelAndView = new ModelAndView();

        // Add userLogInDto object to the ModelAndView
        if (!model.containsAttribute("userLogInDto")) {
            modelAndView.addObject("userLogInDto", new UserLogInDto());
        }

        // Set the view name to "login", assuming "login" is the name of your Thymeleaf template
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @PostMapping("/users/login")
    public ModelAndView logInUser(@Valid UserLogInDto userLogInDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        // Check if username exists
        if (!this.userService.userByUsernameExists(userLogInDto.getUsername()) || !userService.checkPasswordCorrectForTheUsername(userLogInDto)) {
            redirectAttributes.addFlashAttribute("badRequest", "Invalid username or password. Please try again.");
            return new ModelAndView("redirect:/login");
        }

        // If there are errors, redirect to the login page with flash attributes
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLogInDto", userLogInDto);
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            modelAndView.setViewName("redirect:/login");
        } else {
            // If no errors, perform login and redirect to home page
            this.userService.login(userLogInDto);
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @GetMapping("/users/profile")
    public ModelAndView profilePicture() {
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
