package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.configuration.MongockConfig;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.book.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Тестирование Repository Mongo для работы с книгами.
 *
 * @author Mariya Tronina
 */
@DisplayName("BookRepository для работы с книгами должно")
@DataMongoTest
@Import(MongockConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookRepositoryTest {

    public static final int EXPECTED_BOOK_COUNT = 8;
    public static final String DEFAULT_BOOK_ID = "7";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

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
                        ).stream().collect(Collectors.toList()))
                        .authors(Arrays.asList(
                                Author.builder()
                                      .fio("Толстой Лев Николаевич")
                                      .build(),
                                Author.builder()
                                      .fio("Иванов Иван")
                                      .build()
                        ).stream().collect(Collectors.toList()))
                        .build();
        Book expectedBook = bookRepository.save(newBook);
        Book actualBook = mongoTemplate.findOne(Query.query(Criteria.where("id").is(expectedBook.getId())), Book.class);

        assertThat(actualBook).isNotNull().isEqualTo(expectedBook);
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
                                                .id("1")
                                                .name("Роман")
                                                .build(),
                                        Genre.builder()
                                                .id("8")
                                                .name("Фантастика")
                                                .build()
                                ).stream().collect(Collectors.toList()))
                                .authors(Arrays.asList(
                                        Author.builder()
                                                .id("4")
                                                .fio("Стругацкий Аркадий Натанович")
                                                .build(),
                                        Author.builder()
                                                .id("5")
                                                .fio("Стругацкий Борис Натанович")
                                                .build()
                                ).stream().collect(Collectors.toList()))
                                .build();

        Optional<Book> actualBook = bookRepository.findById(DEFAULT_BOOK_ID);
        assertThat(actualBook).isPresent().hasValue(expectedBook);
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
        assertThatCode(() -> {
            bookRepository.deleteById(DEFAULT_BOOK_ID + 1000);
        }).doesNotThrowAnyException();
    }
}
