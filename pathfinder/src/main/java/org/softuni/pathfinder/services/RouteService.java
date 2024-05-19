package org.softuni.pathfinder.services;

import org.softuni.pathfinder.domain.dtos.routes.RouteDto;
import org.softuni.pathfinder.domain.entities.Route;

import java.io.IOException;
import java.util.List;

public interface RouteService {

    void registerRoute(RouteDto routeDto) throws IOException;

    List<Route>  getAllRoutes();

    Route findById(Long id);
}
