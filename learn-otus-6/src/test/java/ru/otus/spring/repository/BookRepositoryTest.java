package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.book.BookRepositoryJpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование Repository JPA для работы с книгами.
 *
 * @author Mariya Tronina
 */
@DisplayName("BookRepository для работы с книгами должно")
@DataJpaTest
@Import(BookRepositoryJpa.class)
public class BookRepositoryTest {

    public static final int EXPECTED_BOOK_COUNT = 8;
    public static final long DEFAULT_BOOK_ID = 7L;

    @Autowired
    private BookRepositoryJpa bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять книгу с авторами и жанрами в БД")
    @Test
    public void shouldInsertBookWithAuthorsAndGenres() {
        Book newBook = Book.builder()
                        .name("Книга")
                        .yearEdition(1987)
                        .genres(Set.of(
                                Genre.builder()
                                     .name("Жанр1")
                                     .build(),
                                Genre.builder()
                                     .name("Роман")
                                     .build()
                        ))
                        .authors(Set.of(
                                Author.builder()
                                      .fio("Толстой Лев Николаевич")
                                      .build(),
                                Author.builder()
                                      .fio("Иванов Иван")
                                      .build()
                        ))
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
                                .genres(Set.of(
                                        Genre.builder()
                                             .name("Роман")
                                             .build(),
                                        Genre.builder()
                                             .name("Фантастика")
                                             .build()
                                ))
                                .authors(Set.of(
                                        Author.builder()
                                              .fio("Струкгацкий Аркадий Натанович")
                                              .build(),
                                        Author.builder()
                                              .fio("Струкгацкий Борис Натанович")
                                              .build()
                                ))
                                .build();

        Optional<Book> actualBook = bookRepository.getById(DEFAULT_BOOK_ID);
        assertThat(actualBook).isNotEmpty();
        assertThat(actualBook.equals(expectedBook));
    }

    @DisplayName("возвращать все книги из БД")
    @Test
    public void shouldGetAllBookWithAuthorsAndGenres() {
        List<Book> actualBookList = bookRepository.getAll();
        assertThat(actualBookList).isNotEmpty()
                                  .hasSize(EXPECTED_BOOK_COUNT)
                                  .allMatch(book -> Objects.nonNull(book));
    }

    @DisplayName("удалять существующую книгу из БД")
    @Test
    public void shouldDeleteExistsBook() {
        boolean deleteExists = bookRepository.deleteById(DEFAULT_BOOK_ID);
        assertThat(deleteExists).isTrue();
    }

    @DisplayName("удалять не существующую книгу из БД")
    @Test
    public void shouldDeleteNotExistsBook() {
        boolean deleteNotExists = bookRepository.deleteById(DEFAULT_BOOK_ID * 1000);
        assertThat(deleteNotExists).isFalse();
    }
}
