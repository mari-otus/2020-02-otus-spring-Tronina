package ru.otus.spring.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.BookNotFoundException;
import ru.otus.spring.repository.book.BookRepository;

import java.util.List;

/**
 * Реализация сервиса для работы с книгами.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    /**
     * Репозиторий для работы с книгами.
     */
    private final BookRepository bookRepository;

    @Override
    public Book addBook(final Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBook(final long bookId) {
        return bookRepository.getById(bookId).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.getAll();
    }

    @Override
    public boolean deleteBook(final long bookId) {
        return bookRepository.deleteById(bookId);
    }
}
