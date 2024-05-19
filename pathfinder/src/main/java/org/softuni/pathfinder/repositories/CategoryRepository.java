package org.softuni.pathfinder.repositories;

import org.softuni.pathfinder.domain.entities.Category;
import org.softuni.pathfinder.domain.entities.enums.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName(CategoryName categoryName);
}

