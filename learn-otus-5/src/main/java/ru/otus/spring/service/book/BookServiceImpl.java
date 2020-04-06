package ru.otus.spring.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.book.BookDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.BookNotFoundException;
import ru.otus.spring.service.author.AuthorService;
import ru.otus.spring.service.genre.GenreService;

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
    /**
     * Сервис для работы с авторами.
     */
    private final AuthorService authorService;
    /**
     * Сервис для работы с жанрами.
     */
    private final GenreService genreService;

    @Override
    public long addBook(final Book book) {
        authorService.addAuthors(book.getAuthors());
        genreService.addGenres(book.getGenres());
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
    public boolean deleteBook(final long bookId) {
        return bookDao.deleteById(bookId);
    }
}
