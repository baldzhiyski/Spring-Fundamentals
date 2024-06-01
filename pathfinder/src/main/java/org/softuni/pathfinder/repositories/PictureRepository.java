package org.softuni.pathfinder.repositories;

import org.softuni.pathfinder.domain.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM pathfinder.pictures AS p WHERE p.route_id = :id ORDER BY RAND() LIMIT 1")
    Picture getRandomPicture(@Param("id") Long id);
}
