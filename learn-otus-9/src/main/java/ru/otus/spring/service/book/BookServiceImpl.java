package ru.otus.spring.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.BookNotFoundException;
import ru.otus.spring.exception.BookRemoveException;
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

    @Transactional
    @Override
    public Book addBook(final Book book) {
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBook(final Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    @Override
    public void deleteBook(final long bookId) {
        try {
            bookRepository.deleteById(bookId);
        } catch (Exception e) {
            throw new BookRemoveException();
        }
    }
}
