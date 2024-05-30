package org.softuni.pathfinder.repositories;

import org.softuni.pathfinder.domain.entities.Category;
import org.softuni.pathfinder.domain.entities.Route;
import org.softuni.pathfinder.domain.entities.enums.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RouteRepository extends JpaRepository<Route,Long> {

    Optional<Route> findById(Long id);

    List<Route> getAllByCategory(Category category);
}
