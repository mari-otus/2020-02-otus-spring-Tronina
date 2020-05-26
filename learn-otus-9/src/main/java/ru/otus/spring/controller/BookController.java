package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.mappers.BookMapper;
import ru.otus.spring.service.book.BookService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с книгами.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@RestController
public class BookController {

    /**
     * Сервис для работы с книгами.
     */
    private final BookService bookService;

    private final BookMapper bookMapper;

    /**
     * Возвращает все книги.
     *
     * @return список книг.
     */
    @GetMapping("books")
    public ResponseEntity<List<BookDto>> getAllBook() {
        List<Book> books = bookService.getAllBook();
        return ResponseEntity.ok().body(books.stream()
                                        .map(book -> bookMapper.bookToBookDto(book))
                                        .collect(Collectors.toList()));
    }

    /**
     * Удаляет книгу по идентификатору.
     *
     * @param id идентификатор книги
     * @return обновленный список книг
     */
    @DeleteMapping("books/{id}")
    public ResponseEntity<List<BookDto>> removeBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        List<Book> books = bookService.getAllBook();
        return ResponseEntity.ok().body(books.stream()
                        .map(book -> bookMapper.bookToBookDto(book))
                        .collect(Collectors.toList()));
    }

    /**
     * Возвращает книгу по идентификатору.
     *
     * @param id идентификатор книги
     * @return книга
     */
    @GetMapping("books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return ResponseEntity.ok().body(bookMapper.bookToBookDto(book));
    }

    /**
     * Добавляет книгу.
     *
     * @param book книга
     * @return книга
     */
    @PostMapping("books")
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto book) {
        Book savedBook = bookService.addBook(bookMapper.bookDtoToBook(book));
        return ResponseEntity.ok().body(bookMapper.bookToBookDto(savedBook));
    }

}
