package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.BookNotFoundException;

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
    private final BookDao bookDao;

    @Override
    public long addBook(final Book book) {
        return bookDao.insert(book);
    }

    @Override
    public Book getBook(final long bookId) {
        return bookDao.getById(bookId).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<Book> getAllBook() {
        return bookDao.getAll();
    }

    @Override
    public int getCountBook() {
        return bookDao.count();
    }

    @Override
    public boolean deleteBook(final long bookId) {
        return bookDao.deleteById(bookId);
    }
}
