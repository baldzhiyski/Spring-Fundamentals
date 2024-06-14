package bg.softuni.book_service.exception;

public class BookAlreadyExists  extends RuntimeException{
    private String message;
    public BookAlreadyExists (String message) {
        super(message);
    }
}
