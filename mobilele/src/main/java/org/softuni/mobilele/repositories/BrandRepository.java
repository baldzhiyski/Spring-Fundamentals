package org.softuni.mobilele.repositories;

import org.softuni.mobilele.domain.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {

    Optional<Brand> findById(Long id);

    @Query("SELECT b FROM Brand b JOIN FETCH b.models")
    List<Brand> findAllWithModels();

    Optional<Brand> findByName(String name);
}
