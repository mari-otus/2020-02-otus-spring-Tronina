package ru.otus.spring.service.book;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.book.BookListEmptyException;
import ru.otus.spring.exception.book.BookNotFoundException;
import ru.otus.spring.exception.book.BookNullPointerException;
import ru.otus.spring.exception.book.BookRemoveException;
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
        if (book == null) {
            throw new BookNullPointerException();
        }
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
        List<Book> books = bookRepository.findAllByOrderByIdAsc();
        if (CollectionUtils.isEmpty(books)) {
            throw new BookListEmptyException();
        }
        return books;
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
