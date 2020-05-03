package ru.otus.spring.exception;

/**
 * Исключение, если книга не может быть удалена.
 *
 * @author Mariya Tronina
 */
public class BookRemoveException extends RuntimeException {

    private static final String BOOK_REMOVE_MESSAGE = "Книга не может быть удалена";

    public BookRemoveException() {
        super(BOOK_REMOVE_MESSAGE);
    }
}
