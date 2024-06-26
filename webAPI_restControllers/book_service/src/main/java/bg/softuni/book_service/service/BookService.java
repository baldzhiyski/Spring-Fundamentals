package bg.softuni.book_service.service;

import bg.softuni.book_service.model.dtos.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDTO> getAllBooks();

    Optional<BookDTO> findBookById(Long id);

    void deleteBookByID(Long id);

    Long createBook(BookDTO bookDTO);
}
