package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.book.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Тестирование Repository JPA для работы с книгами.
 *
 * @author Mariya Tronina
 */
@DisplayName("BookRepository для работы с книгами должно")
@DataJpaTest
public class BookRepositoryTest {

    public static final int EXPECTED_BOOK_COUNT = 8;
    public static final long DEFAULT_BOOK_ID = 7L;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять книгу с авторами и жанрами в БД")
    @Test
    public void shouldInsertBookWithAuthorsAndGenres() {
        Book newBook = Book.builder()
                        .name("Книга")
                        .yearEdition(1987)
                        .genres(Arrays.asList(
                                Genre.builder()
                                     .name("Жанр1")
                                     .build(),
                                Genre.builder()
                                     .name("Роман")
                                     .build()
                        ).stream().collect(Collectors.toSet()))
                        .authors(Arrays.asList(
                                Author.builder()
                                      .fio("Толстой Лев Николаевич")
                                      .build(),
                                Author.builder()
                                      .fio("Иванов Иван")
                                      .build()
                        ).stream().collect(Collectors.toSet()))
                        .build();
        Book expectedBook = bookRepository.save(newBook);
        Book actualBook = em.find(Book.class, expectedBook.getId());

        assertThat(actualBook).isNotNull();
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
                                ).stream().collect(Collectors.toSet()))
                                .authors(Arrays.asList(
                                        Author.builder()
                                              .fio("Струкгацкий Аркадий Натанович")
                                              .build(),
                                        Author.builder()
                                              .fio("Струкгацкий Борис Натанович")
                                              .build()
                                ).stream().collect(Collectors.toSet()))
                                .build();

        Book actualBook = bookRepository.getOne(DEFAULT_BOOK_ID);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.equals(expectedBook));
    }

    @DisplayName("возвращать все книги из БД")
    @Test
    public void shouldGetAllBookWithAuthorsAndGenres() {
        List<Book> actualBookList = bookRepository.findAll();
        assertThat(actualBookList).isNotEmpty()
                                  .hasSize(EXPECTED_BOOK_COUNT)
                                  .allMatch(book -> Objects.nonNull(book));
    }

    @DisplayName("удалять существующую книгу из БД")
    @Test
    public void shouldDeleteExistsBook() {
        assertThatCode(() -> {
            bookRepository.deleteById(DEFAULT_BOOK_ID);
        }).doesNotThrowAnyException();
    }

    @DisplayName("удалять не существующую книгу из БД")
    @Test
    public void shouldDeleteNotExistsBook() {
        assertThatThrownBy(() -> {
            bookRepository.deleteById(DEFAULT_BOOK_ID * 1000);
        }).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
