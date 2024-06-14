package bg.softuni.book_service.repository;

import bg.softuni.book_service.model.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<BookEntity,Long> {
}
