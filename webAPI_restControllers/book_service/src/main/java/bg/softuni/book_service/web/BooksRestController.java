package bg.softuni.book_service.web;


import bg.softuni.book_service.exception.BookAlreadyExists;
import bg.softuni.book_service.model.dtos.BookDTO;
import bg.softuni.book_service.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/books")
public class BooksRestController {

    private final BookService bookService;

    public BooksRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findBookById(@PathVariable("id") Long id) {
        Optional<BookDTO> bookDTOOptional = bookService.findBookById(id);

        return bookDTOOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBookByID(@PathVariable("id") Long id) {

        bookService.deleteBookByID(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @RequestBody BookDTO bookDTO,
            UriComponentsBuilder uriComponentsBuilder) {

        long newBookID = bookService.createBook(bookDTO);

        return ResponseEntity.created(
                uriComponentsBuilder.path("/api/books/{id}").build(newBookID)
        ).build();
    }

    @ExceptionHandler(BookAlreadyExists.class)
    public ResponseEntity<String> handleBookAlreadyExistsException(BookAlreadyExists ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}