package bg.softuni.exam.repository;

import bg.softuni.exam.model.entity.Painting;
import bg.softuni.exam.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("SELECT DISTINCT p FROM Painting p " +
            "WHERE p.owner <> :user " +
            "AND p NOT IN (SELECT fp FROM User u JOIN u.favouritePaintings fp WHERE u = :user)")
    Set<Painting> findAllByOwnerNotAndNotInFavouritePaintings(@Param("user") User user);
    List<User> findAllByFavouritePaintingsContaining(Painting painting);
}
