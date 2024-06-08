package org.softuni.pathfinder.web;

import jakarta.validation.Valid;
import org.softuni.pathfinder.domain.dtos.comments.CommentDto;
import org.softuni.pathfinder.domain.dtos.routes.RouteCategoryViewModel;
import org.softuni.pathfinder.domain.dtos.routes.RouteDto;
import org.softuni.pathfinder.domain.dtos.routes.RouteWithRandomPicDto;
import org.softuni.pathfinder.domain.entities.Comment;
import org.softuni.pathfinder.domain.entities.Picture;
import org.softuni.pathfinder.domain.entities.Route;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.domain.entities.enums.CategoryName;
import org.softuni.pathfinder.repositories.RouteRepository;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.CommentService;
import org.softuni.pathfinder.services.PictureService;
import org.softuni.pathfinder.services.RouteService;
import org.softuni.pathfinder.services.UserService;
import org.softuni.pathfinder.utils.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.softuni.pathfinder.domain.entities.enums.CategoryName.*;

@Controller
public class RoutesController {
    private RouteService routeService;
    private LoggedInUser logged;

    private CommentService commentService;
    private UserService userService;

    private PictureService pictureService;

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";


    @Autowired
    public RoutesController(RouteRepository routeRepository, RouteService routeService, LoggedInUser logged, CommentService commentService, UserService userService, PictureService pictureService) {
        this.routeService = routeService;
        this.logged = logged;
        this.commentService = commentService;
        this.userService = userService;
        this.pictureService = pictureService;
    }

    @ModelAttribute("loggedIn")
    public boolean isLogged(){
        return this.userService.isLoggedIn();
    }

    @GetMapping("/routes")
    public ModelAndView getRoutes() {
        ModelAndView modelAndView = new ModelAndView();
        if(!this.userService.isLoggedIn()){
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        List<Route> routes = this.routeService.getAllRoutes();
        List<RouteWithRandomPicDto> routeWithRandomPics = new ArrayList<>();
        for (Route route : routes) {
            Picture randomPicture = this.pictureService.getRandomPicture(route.getId());
            RouteWithRandomPicDto dto = new RouteWithRandomPicDto();

            dto.setId(route.getId());
            dto.setDescription(route.getDescription());
            dto.setName(route.getName());
            dto.setRandomImageUrl(randomPicture.getUrl());

            routeWithRandomPics.add(dto);
        }

        modelAndView.setViewName("routes");
        modelAndView.addObject("routeWithRandomPics", routeWithRandomPics);

        return modelAndView;
    }

    // TODO : Get a map for the route and also calculate the distance. How ??? Still needed to be implemented
    @GetMapping("/routes/details/{id}")
    public ModelAndView getDetailInfo(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        if(!this.userService.isLoggedIn()){
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        Route route = this.routeService.findById(id);

        Set<Comment> comments = route.getComments();
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("route", route);
        modelAndView.setViewName("route-details");
        return modelAndView;
    }


    // TODO : Fix the comments section in the view and also here ! It should be displayed better.
    // TODO: Add functionality for admins and moderators
    @PostMapping("/routes/details/{id}")
    public ModelAndView postComment(@PathVariable Long id, @Valid CommentDto commentDto,
                                    BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Route route = this.routeService.findById(id);
        User user = this.userService.getById(logged.getId());

        modelAndView.addObject("route", route);
        modelAndView.setViewName("route-details");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("routeId", id);
            modelAndView.addObject("commentDto", commentDto);
            modelAndView.setViewName("route-details");
        } else {
            commentDto.setAuthor(user);
            commentDto.setCreated(new Date());
            commentDto.setRoute(route);

            this.commentService.registerComment(commentDto);

            modelAndView.setViewName("redirect:/home");
        }


        return modelAndView;
    }

    @GetMapping("/routes/add")
    public ModelAndView addPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        if(!this.userService.isLoggedIn()){
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        modelAndView.addObject("isAtPage", true);

        if (!model.containsAttribute("routeDto")) {
            model.addAttribute("routeDto", new RouteDto());
        }

        modelAndView.setViewName("add-route");
        return modelAndView;
    }

    @PostMapping("/routes/add")
    public ModelAndView addRoute(@Valid RouteDto routeDto, BindingResult bindingResult
            , RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("isAtPage", true);

        if (bindingResult.hasErrors()) {
            final String attributeName = "routeDto";
            redirectAttributes
                    .addFlashAttribute(attributeName, routeDto)
                    .addFlashAttribute(bindingResultPath + DOT + attributeName, bindingResult);
            modelAndView.setViewName("redirect:/routes/add");
        } else {
            routeService.registerRoute(routeDto);
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @GetMapping("/routes/{categoryName}")
    public ModelAndView getAllByCategory(@PathVariable("categoryName") CategoryName categoryName) {
        ModelAndView modelAndView = new ModelAndView();
        if(!this.userService.isLoggedIn()){
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        List<RouteCategoryViewModel> routes = routeService.getAllByCategory(categoryName);
        routes.forEach(route -> route.setRandomPicUrl(this.pictureService.getRandomPicture(route.getId()).getUrl()));

        String view =
                switch (categoryName) {
                    case PEDESTRIAN -> "pedestrian";
                    case MOTORCYCLE -> "motorcycle";
                    case CAR -> "car";
                    case BICYCLE -> "bicycle";
                };

        modelAndView.setViewName(view);

        modelAndView.addObject("routes", routes);

        return modelAndView;
    }

}
