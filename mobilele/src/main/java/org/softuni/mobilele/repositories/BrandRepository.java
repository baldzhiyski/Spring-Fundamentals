package org.softuni.mobilele.repositories;

import org.softuni.mobilele.domain.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {

    Optional<Brand> findById(Long id);
}
