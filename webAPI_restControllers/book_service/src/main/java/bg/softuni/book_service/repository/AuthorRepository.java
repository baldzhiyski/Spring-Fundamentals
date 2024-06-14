package bg.softuni.book_service.repository;

import bg.softuni.book_service.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
    Optional<AuthorEntity> findByName(String authorName);
}
