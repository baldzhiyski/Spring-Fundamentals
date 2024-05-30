package org.softuni.pathfinder.web;

import jakarta.validation.Valid;
import org.softuni.pathfinder.domain.dtos.comments.CommentDto;
import org.softuni.pathfinder.domain.dtos.routes.RouteCategoryViewModel;
import org.softuni.pathfinder.domain.dtos.routes.RouteDto;
import org.softuni.pathfinder.domain.entities.Comment;
import org.softuni.pathfinder.domain.entities.Route;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.domain.entities.enums.CategoryName;
import org.softuni.pathfinder.repositories.RouteRepository;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.CommentService;
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

    @Value("${binding-result-package}")
    private String bindingResultPath;
    private static final String DOT = ".";


    @Autowired
    public RoutesController(RouteRepository routeRepository, RouteService routeService, LoggedInUser logged, CommentService commentService, UserService userService) {
        this.routeService = routeService;
        this.logged = logged;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/routes")
    public ModelAndView getRoutes(){
        ModelAndView modelAndView = new ModelAndView();

        boolean loggedIn = this.userService.isLoggedIn();
        modelAndView.addObject("loggedIn", loggedIn);
        List<Route> routes = this.routeService.getAllRoutes();

        modelAndView.setViewName("routes");
        modelAndView.addObject("routes",routes);

        return modelAndView;
    }

    // TODO : Get a map for the route and also calculate the distance. How ??? Still needed to be implemented
    @GetMapping("/routes/details/{id}")
    public ModelAndView getDetailInfo(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();

        boolean loggedIn = this.userService.isLoggedIn();
        modelAndView.addObject("loggedIn", loggedIn);

        Route route = this.routeService.findById(id);


        Set<Comment> comments = route.getComments();
        modelAndView.addObject("comments",comments);

        modelAndView.addObject("route",route);
        modelAndView.setViewName("route-details");
        return modelAndView;
    }


    // TODO : Fix the comments section in the view and also here ! It should be displayed better.
    // TODO: Add functionality for admins and moderators
    @PostMapping("/routes/details/{id}")
    public  ModelAndView postComment(@PathVariable Long id , @Valid CommentDto commentDto,
                                     BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        Route route = this.routeService.findById(id);
        User user = this.userService.getById(logged.getId());

        modelAndView.addObject("route",route);
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
    public ModelAndView addPage(){
        ModelAndView modelAndView = new ModelAndView();
        boolean loggedIn = this.userService.isLoggedIn();
        modelAndView.addObject("loggedIn", loggedIn);
        modelAndView.addObject("isAtPage",true);
        modelAndView.addObject("routeDto",new RouteDto());

        modelAndView.setViewName("add-route");
        return modelAndView;

    }

    @PostMapping("/routes/add")
    public ModelAndView addRoute(@Valid RouteDto routeDto, BindingResult bindingResult
    , RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        boolean loggedIn = this.userService.isLoggedIn();
        modelAndView.addObject("loggedIn", loggedIn);
        modelAndView.addObject("isAtPage", true);

        // Perform file type validation
        MultipartFile gpxCoordinates = routeDto.getGpxCoordinates();
        if (gpxCoordinates != null) {
            String contentType = gpxCoordinates.getContentType();
            if (!"text/plain".equals(contentType)) {
                bindingResult.rejectValue("gpxCoordinates", "error.routeDto", "Invalid file type. Please upload a .txt file.");
            }
        }

        if (bindingResult.hasErrors()) {
            final String attributeName = "routeDto";
            redirectAttributes
                    .addFlashAttribute(attributeName, routeDto)
                    .addFlashAttribute(bindingResultPath + DOT + attributeName, bindingResult);
            modelAndView.setViewName("add-route");
        } else {
            routeService.registerRoute(routeDto);
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }
    @GetMapping("/routes/{categoryName}")
    public ModelAndView getAllByCategory(@PathVariable("categoryName") CategoryName categoryName) {
        List<RouteCategoryViewModel> routes = routeService.getAllByCategory(categoryName);

        String view =
                switch (categoryName) {
                    case PEDESTRIAN -> "pedestrian";
                    case MOTORCYCLE -> "motorcycle";
                    case CAR -> "car";
                    case BICYCLE -> "bicycle";
                };

        ModelAndView modelAndView = new ModelAndView(view);

        modelAndView.addObject("routes", routes);

        return modelAndView;
    }

}
