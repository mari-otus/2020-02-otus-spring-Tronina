package ru.otus.spring.exception;

/**
 * Исключение, если книга не найдена.
 *
 * @author Mariya Tronina
 */
public class BookNotFoundException extends RuntimeException {

    private static final String BOOK_NOT_FOUND_MESSAGE = "Книга не найдена";

    public BookNotFoundException() {
        super(BOOK_NOT_FOUND_MESSAGE);
    }
}
