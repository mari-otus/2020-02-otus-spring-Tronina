package ru.otus.spring.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.otus.spring.exception.book.BookListEmptyException;
import ru.otus.spring.exception.book.BookNotFoundException;
import ru.otus.spring.exception.book.BookNullPointerException;
import ru.otus.spring.exception.comment.CommentBookListEmptyException;

/**
 * Обработчик исключений контроллеров.
 *
 * @author Mariya Tronina
 */
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({BookListEmptyException.class, CommentBookListEmptyException.class})
    public ResponseEntity<Object> handleNoContent(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }

    @ExceptionHandler(BookNullPointerException.class)
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
