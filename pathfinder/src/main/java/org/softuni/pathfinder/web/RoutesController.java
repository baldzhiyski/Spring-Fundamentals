package org.softuni.pathfinder.web;

import jakarta.validation.Valid;
import org.softuni.pathfinder.domain.dtos.comments.CommentDto;
import org.softuni.pathfinder.domain.dtos.routes.RouteDto;
import org.softuni.pathfinder.domain.entities.Comment;
import org.softuni.pathfinder.domain.entities.Route;
import org.softuni.pathfinder.domain.entities.User;
import org.softuni.pathfinder.repositories.RouteRepository;
import org.softuni.pathfinder.repositories.UserRepository;
import org.softuni.pathfinder.services.CommentService;
import org.softuni.pathfinder.services.RouteService;
import org.softuni.pathfinder.services.UserService;
import org.softuni.pathfinder.utils.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class RoutesController {
  private RouteService routeService;
    private LoggedInUser logged;

    private CommentService commentService;
    private UserService userService;

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

    // TODO : Get a map for the route and also calculate the distance. How ???
    @GetMapping("/route/details/{id}")
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

    @PostMapping("/route/details/{id}")
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

        modelAndView.setViewName("add-route");
        return modelAndView;

    }

    // TODO : PostMapping to the add route page
    @PostMapping("/routes/add")
    public ModelAndView addRoute(@Valid RouteDto routeDto, BindingResult bindingResult) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        boolean loggedIn = this.userService.isLoggedIn();
        modelAndView.addObject("loggedIn", loggedIn);
        modelAndView.addObject("isAtPage",true);

        if(bindingResult.hasErrors()){


            modelAndView.setViewName("add-route");
        }else{

            this.routeService.registerRoute(routeDto);
            modelAndView.setViewName("redirect:/home");
        }

        return modelAndView;
    }
}
