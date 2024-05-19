package org.softuni.pathfinder.repositories;

import org.softuni.pathfinder.domain.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route,Long> {

    Optional<Route> findById(Long id);
}
