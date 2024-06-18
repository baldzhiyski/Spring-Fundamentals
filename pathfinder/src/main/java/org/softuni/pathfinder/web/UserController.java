package org.softuni.pathfinder.web;

import jakarta.validation.Valid;
import org.softuni.pathfinder.domain.dtos.user.UserRegisterDto;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.services.UserService;
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
    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
    public String login(Model model) {
        if(!model.containsAttribute("username")){
            model.addAttribute("username","");
        }
        return "login";
    }

    @PostMapping("/users/login-error")
    public String onFailure(
            @ModelAttribute("username") String username,
            Model model) {

        model.addAttribute("username", username);
        model.addAttribute("badRequest", "Invalid username or password !");

        return "login";
    }


    @GetMapping("/users/profile")
    public ModelAndView profilePicture() {
        ModelAndView modelAndView = new ModelAndView("profile");

        User user = this.userService.getByUsername(this.userService.getLoggedInUser().getUsername());
        modelAndView.addObject("loggedIn", user);

        return modelAndView;
    }


}
