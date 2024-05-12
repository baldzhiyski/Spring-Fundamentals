package org.softuni.mobilele.repositories;

import org.softuni.mobilele.domain.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model,Long> {

    Optional<Model> findById(Long id);
}
