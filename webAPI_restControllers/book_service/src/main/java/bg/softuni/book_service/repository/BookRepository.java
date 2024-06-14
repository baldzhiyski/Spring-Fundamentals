package bg.softuni.book_service.repository;

import bg.softuni.book_service.model.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository  extends JpaRepository<BookEntity,Long> {
    Optional<BookEntity> findByTitle(String title);
}
