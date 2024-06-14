package bg.softuni.book_service.service.impl;

import bg.softuni.book_service.model.dtos.AuthorDTO;
import bg.softuni.book_service.model.dtos.BookDTO;
import bg.softuni.book_service.model.entity.AuthorEntity;
import bg.softuni.book_service.model.entity.BookEntity;
import bg.softuni.book_service.repository.AuthorRepository;
import bg.softuni.book_service.repository.BookRepository;
import bg.softuni.book_service.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(BookServiceImpl::mapBookToDTO)
                .toList();
    }

    private static BookDTO mapBookToDTO(BookEntity bookEntity) {
        AuthorDTO authorDTO = new AuthorDTO().setName(bookEntity.getAuthor().getName());
        return new BookDTO()
                .setAuthor(authorDTO)
                .setId(bookEntity.getId())
                .setIsbn(bookEntity.getIsbn())
                .setTitle(bookEntity.getTitle());
    }

    @Override
    public Optional<BookDTO> findBookById(Long id) {
        return this.bookRepository.findById(id).map(BookServiceImpl::mapBookToDTO);
    }

    @Override
    public void deleteBookByID(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Long createBook(BookDTO bookDTO) {
        Optional<AuthorEntity> authorOpt = authorRepository.findByName(bookDTO.getAuthor().getName());

        BookEntity newBook = new BookEntity()
                .setAuthor(authorOpt.orElseGet(() ->
                        authorRepository.save(new AuthorEntity()
                                .setName(bookDTO.getAuthor().getName()))))
                .setIsbn(bookDTO.getIsbn())
                .setTitle(bookDTO.getTitle());

        newBook = bookRepository.save(newBook);

        return newBook.getId();
    }
}
