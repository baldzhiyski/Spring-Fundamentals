package org.softuni.pathfinder.web;

import org.softuni.pathfinder.domain.entities.Route;
import org.softuni.pathfinder.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RoutesController {
    private RouteRepository routeRepository;

    @Autowired
    public RoutesController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping("/routes")
    public ModelAndView getRoutes(){
        ModelAndView modelAndView = new ModelAndView();

        List<Route> routes = this.routeRepository.findAll();

        modelAndView.setViewName("routes");
        modelAndView.addObject("routes",routes);

        return modelAndView;
    }

    @GetMapping("/route/details/{id}")
    public ModelAndView getDetailInfo(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();

        Route route = this.routeRepository.findById(id).orElseThrow();

        modelAndView.addObject("route",route);
        modelAndView.setViewName("route-details");
        return modelAndView;
    }
}
