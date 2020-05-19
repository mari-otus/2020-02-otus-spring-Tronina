package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
 * @author Mariya Tronina mariya.tronina@rtlabs.ru
 */
@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    private final BookMapper bookMapper;

    @GetMapping("books")
    public ResponseEntity<List<BookDto>> getAllBook() {
        List<Book> books = bookService.getAllBook();
        return CollectionUtils.isEmpty(books)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok()
                                .body(books.stream()
                                        .map(book -> bookMapper.bookToBookDto(book))
                                        .collect(Collectors.toList()));
    }

    @DeleteMapping("books/{id}")
    public ResponseEntity<List<BookDto>> removeBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        List<Book> books = bookService.getAllBook();
        return CollectionUtils.isEmpty(books)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok()
                .body(books.stream()
                        .map(book -> bookMapper.bookToBookDto(book))
                        .collect(Collectors.toList()));
    }

    @GetMapping("books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return book == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok()
                .body(bookMapper.bookToBookDto(book));
    }

    @PostMapping("books")
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto book) {
        Book savedBook = bookService.addBook(bookMapper.bookDtoToBook(book));
        return savedBook == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok()
                .body(bookMapper.bookToBookDto(savedBook));
    }

}
