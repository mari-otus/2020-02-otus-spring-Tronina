package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.book.BookDaoJdbc;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование DAO JDBC для работы с книгами.
 *
 * @author Mariya Tronina
 */
@DisplayName("BookDao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoTest {

    public static final int EXPECTED_BOOK_COUNT = 8;
    public static final long DEFAULT_BOOK_ID = 7L;

    @Autowired
    BookDaoJdbc bookDao;

    @DisplayName("добавлять книгу с авторами и жанрами в БД")
    @Test
    public void shouldInsertBookWithAuthorsAndGenres() {
        Book expectedBook = Book.builder()
                        .name("Книга")
                        .yearEdition(1987)
                        .genres(Arrays.asList(
                                Genre.builder()
                                     .name("Приключение")
                                     .build(),
                                Genre.builder()
                                     .name("Роман")
                                     .build()
                        ))
                        .authors(Arrays.asList(
                                Author.builder()
                                      .fio("Толстой Лев Николаевич")
                                      .build()
                        ))
                        .build();
        long bookId = bookDao.insert(expectedBook);

        Optional<Book> actualBook = bookDao.getById(bookId);
        assertThat(actualBook).isNotEmpty();
        assertThat(actualBook.equals(expectedBook));
    }

    @DisplayName("возвращать ожидаемую книгу с авторами и жанрами по идентификатору из БД")
    @Test
    public void shouldGetBookByIdWithAuthorsAndGenres() {
        Book expectedBook = Book.builder()
                                .id(DEFAULT_BOOK_ID)
                                .name("Улитка на склоне")
                                .yearEdition(2001)
                                .genres(Arrays.asList(
                                        Genre.builder()
                                             .name("Роман")
                                             .build(),
                                        Genre.builder()
                                             .name("Фантастика")
                                             .build()
                                ))
                                .authors(Arrays.asList(
                                        Author.builder()
                                              .fio("Стругацкий Аркадий Натанович")
                                              .build(),
                                        Author.builder()
                                              .fio("Стругацкий Борис Натанович")
                                              .build()
                                ))
                                .build();

        Optional<Book> actualBook = bookDao.getById(DEFAULT_BOOK_ID);
        assertThat(actualBook).isNotEmpty();
        assertThat(actualBook.equals(expectedBook));
    }

    @DisplayName("возвращать все книги из БД")
    @Test
    public void shouldGetAllBookWithAuthorsAndGenres() {
        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList).isNotEmpty()
                                  .hasSize(EXPECTED_BOOK_COUNT)
                                  .allMatch(book -> Objects.nonNull(book));
    }

    @DisplayName("удалять существующую книгу из БД")
    @Test
    public void shouldDeleteExistsBook() {
        boolean deleteExists = bookDao.deleteById(DEFAULT_BOOK_ID);
        assertThat(deleteExists).isTrue();
    }

    @DisplayName("удалять не существующую книгу из БД")
    @Test
    public void shouldDeleteNotExistsBook() {
        boolean deleteNotExists = bookDao.deleteById(DEFAULT_BOOK_ID * 1000);
        assertThat(deleteNotExists).isFalse();
    }
}
