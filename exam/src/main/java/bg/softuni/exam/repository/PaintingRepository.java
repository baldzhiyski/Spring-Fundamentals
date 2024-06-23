package bg.softuni.exam.repository;


import bg.softuni.exam.model.entity.Painting;
import bg.softuni.exam.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PaintingRepository  extends JpaRepository<Painting, UUID> {

    Set<Painting> findAllByOwner(User user);

    @Query(value = "SELECT * FROM painting_collectors.paintings ORDER BY votes DESC, name LIMIT 2", nativeQuery = true)
    Set<Painting> findTop2ByOrderByVotesDescAndName();

}
