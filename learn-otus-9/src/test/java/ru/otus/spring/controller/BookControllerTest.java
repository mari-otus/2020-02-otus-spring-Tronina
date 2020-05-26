package ru.otus.spring.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование контроллера для работы с книгами.
 *
 * @author Mariya Tronina
 */
@DisplayName("Контроллер BookController должен")
@Sql(value = { "classpath:clearData.sql", "classpath:data.sql" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();
    private static final HttpEntity REQUEST_HTTP_ENTITY = new HttpEntity(HTTP_HEADERS);

    private static final int BOOK_COUNT = 8;
    private static final List<BookDto> BOOK_LIST = new ArrayList<>();
    private static final Long BOOK_ID_DEFAULT = 7L;

    @BeforeAll
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/library";

        HTTP_HEADERS.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));

        BOOK_LIST.clear();
        BOOK_LIST.addAll(Stream.of(
                BookDto.builder().id(1L).name("Война и мир")
                        .yearEdition(1981)
                        .authors(Set.of(Author.builder().id(1L).fio("Толстой Лев Николаевич").build()))
                        .genres(Set.of(Genre.builder().id(1L).name("Роман").build()))
                        .build(),
                BookDto.builder().id(2L).name("Хаджи-Мурат")
                        .yearEdition(1975)
                        .authors(Set.of(Author.builder().id(1L).fio("Толстой Лев Николаевич").build()))
                        .genres(Set.of(Genre.builder().id(3L).name("Повесть").build()))
                        .build(),
                BookDto.builder().id(3L).name("Евгений Онегин")
                        .yearEdition(1987)
                        .authors(Set.of(Author.builder().id(2L).fio("Пушкин Александр Сергеевич").build()))
                        .genres(Set.of(Genre.builder().id(1L).name("Роман").build(), Genre.builder().id(4L).name("Стихотворение").build()))
                        .build(),
                BookDto.builder().id(4L).name("Сказка о рыбаке и рыбке")
                        .yearEdition(2015)
                        .authors(Set.of(Author.builder().id(2L).fio("Пушкин Александр Сергеевич").build()))
                        .genres(Set.of(Genre.builder().id(4L).name("Стихотворение").build(), Genre.builder().id(5L).name("Сказка").build()))
                        .build(),
                BookDto.builder().id(5L).name("Песнь о вещем Олеге")
                        .yearEdition(2015)
                        .authors(Set.of(Author.builder().id(2L).fio("Пушкин Александр Сергеевич").build()))
                        .genres(Set.of(Genre.builder().id(3L).name("Повесть").build(), Genre.builder().id(4L).name("Стихотворение").build()))
                        .build(),
                BookDto.builder().id(6L).name("Полное собрание повестей и рассказов о Шерлоке Холмсе в одном томе")
                        .yearEdition(1998)
                        .authors(Set.of(Author.builder().id(3L).fio("Дойл Артур Конан").build()))
                        .genres(Set.of(Genre.builder().id(2L).name("Рассказ").build(), Genre.builder().id(3L).name("Повесть").build()))
                        .build(),
                BookDto.builder().id(7L).name("Улитка на склоне")
                        .yearEdition(2001)
                        .authors(Set.of(Author.builder().id(4L).fio("Стругацкий Аркадий Натанович").build(), Author.builder().id(5L).fio("Стругацкий Борис Натанович").build()))
                        .genres(Set.of(Genre.builder().id(1L).name("Роман").build(), Genre.builder().id(8L).name("Фантастика").build()))
                        .build(),
                BookDto.builder().id(8L).name("Понедельник начинается в субботу")
                        .yearEdition(2001)
                        .authors(Set.of(Author.builder().id(4L).fio("Стругацкий Аркадий Натанович").build(), Author.builder().id(5L).fio("Стругацкий Борис Натанович").build()))
                        .genres(Set.of(Genre.builder().id(3L).name("Повесть").build(), Genre.builder().id(8L).name("Фантастика").build(), Genre.builder().id(9L).name("Юмор").build()))
                        .build()
        ).collect(Collectors.toList()));
    }

    @DisplayName("возвращать все книги")
    @Test
    public void shouldGetAllBooks() {
        final ResponseEntity<List<BookDto>> result = this.restTemplate
                .exchange(baseUrl + "/books",
                        HttpMethod.GET,
                        REQUEST_HTTP_ENTITY,
                        new ParameterizedTypeReference<List<BookDto>>() {
                        });

        assertThat(result.getStatusCode())
                .as("Статус ответа должен быть 200 OK")
                .isEqualTo(HttpStatus.OK);

        assertThat(result.getBody())
                .as("Ответ не должен быть пустым")
                .isNotEmpty()
                .as("Ответ должен содержать определенное количество книг")
                .hasSize(BOOK_COUNT)
                .as("Ответ должен содержать все указанные книги")
                .containsExactlyInAnyOrderElementsOf(BOOK_LIST);
    }

    @Sql(value = "classpath:clearData.sql")
    @DisplayName("обработать ответ с пустым списком книг")
    @Test
    public void shouldGetAllBooksWithEmpty() {
        final ResponseEntity<List<BookDto>> result = this.restTemplate
                .exchange(baseUrl + "/books",
                        HttpMethod.GET,
                        REQUEST_HTTP_ENTITY,
                        new ParameterizedTypeReference<List<BookDto>>() {
                        });

        assertThat(result.getStatusCode())
                .as("Статус ответа должен быть 400 NO_CONTENT")
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @DisplayName("обработать запрос на получении книги по несуществющему идентификатору")
    @Test
    public void shouldGetBookByNoExistsId() {
        final ResponseEntity<BookDto> result = this.restTemplate
                .exchange(baseUrl + "/books/{id}",
                        HttpMethod.GET,
                        REQUEST_HTTP_ENTITY,
                        BookDto.class,
                        BOOK_ID_DEFAULT + 1000);

        assertThat(result.getStatusCode())
                .as("Статус ответа должен быть 404 NOT_FOUND")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    public void shouldGetBookById() {
        final ResponseEntity<BookDto> result = this.restTemplate
                .exchange(baseUrl + "/books/{id}",
                        HttpMethod.GET,
                        REQUEST_HTTP_ENTITY,
                        BookDto.class,
                        BOOK_ID_DEFAULT);

        assertThat(result.getStatusCode())
                .as("Статус ответа должен быть 200 OK")
                .isEqualTo(HttpStatus.OK);

        assertThat(result.getBody())
                .as("Ответ не должен быть пустым")
                .isNotNull()
                .as("Ответ должен содержать указанную книгу")
                .isEqualTo(BOOK_LIST.stream()
                        .filter(book -> book.getId() == BOOK_ID_DEFAULT)
                        .findAny().get());
    }

    @DisplayName("удалять книгу по идентификатору")
    @Test
    public void shouldRemoveBookById() {
        final ResponseEntity<List<BookDto>> result = this.restTemplate
                .exchange(baseUrl + "/books/{id}",
                        HttpMethod.DELETE,
                        REQUEST_HTTP_ENTITY,
                        new ParameterizedTypeReference<List<BookDto>>() {
                        },
                        BOOK_ID_DEFAULT);

        assertThat(result.getStatusCode())
                .as("Статус ответа должен быть 200 OK")
                .isEqualTo(HttpStatus.OK);

        assertThat(result.getBody())
                .as("Ответ не должен быть пустым")
                .isNotEmpty()
                .as("Ответ должен содержать определенное количество книг")
                .hasSize(BOOK_COUNT - 1)
                .as("Ответ должен содержать все указанные книги, кроме удаленной")
                .containsExactlyInAnyOrderElementsOf(BOOK_LIST.stream()
                        .filter(book -> book.getId() != BOOK_ID_DEFAULT)
                        .collect(Collectors.toList()))
                .as("Ответ не должен содержать удаленной книги")
                .doesNotContain(BOOK_LIST.stream()
                        .filter(book -> book.getId() == BOOK_ID_DEFAULT)
                        .findAny().get());
    }

    @DisplayName("добавлять новую книгу")
    @Test
    public void shouldAddBook() {
        final BookDto newBook = BookDto.builder().id(9L).name("BOOK")
                .yearEdition(2000)
                .authors(Set.of(Author.builder().fio("AUTHOR1").build(), Author.builder().fio("AUTHOR2").build()))
                .genres(Set.of(Genre.builder().name("GENRE1").build(), Genre.builder().name("GENRE2").build()))
                .build();

        final ResponseEntity<BookDto> result = this.restTemplate
                .exchange(baseUrl + "/books",
                        HttpMethod.POST,
                        new HttpEntity<>(newBook, HTTP_HEADERS),
                        BookDto.class);

        assertThat(result.getStatusCode())
                .as("Статус ответа должен быть 200 OK")
                .isEqualTo(HttpStatus.OK);

        assertThat(result.getBody())
                .as("Ответ не должен быть пустым")
                .isNotNull()
                .as("Ответ должен содержать новую книгу")
                .matches(expectedBook ->
                        expectedBook.getName().equals(newBook.getName()) &&
                                expectedBook.getYearEdition() == newBook.getYearEdition() &&
                                expectedBook.getId() != null &&
                                CollectionUtils.size(expectedBook.getAuthors()) == 2 &&
                                CollectionUtils.size(expectedBook.getGenres()) == 2
                );
    }

    @DisplayName("обработать случай при добавлении книги = null")
    @Test
    public void shouldAddBookIsNull() {
        final ResponseEntity<BookDto> result = this.restTemplate
                .exchange(baseUrl + "/books",
                        HttpMethod.POST,
                        new HttpEntity<>(null, HTTP_HEADERS),
                        BookDto.class);

        assertThat(result.getStatusCode())
                .as("Статус ответа должен быть 400 BAD_REQUEST")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @DisplayName("изменять существующую книгу")
    @Test
    public void shouldEditBook() {
        final BookDto existsBook = BOOK_LIST.stream()
                .filter(book -> book.getId() == BOOK_ID_DEFAULT)
                .findAny().get();
        existsBook.setName("existsBook");
        final ResponseEntity<BookDto> result = this.restTemplate
                .exchange(baseUrl + "/books",
                        HttpMethod.POST,
                        new HttpEntity<>(existsBook, HTTP_HEADERS),
                        BookDto.class);

        assertThat(result.getStatusCode())
                .as("Статус ответа должен быть 200 OK")
                .isEqualTo(HttpStatus.OK);

        assertThat(result.getBody())
                .as("Ответ не должен быть пустым")
                .isNotNull()
                .as("Ответ должен содержать измененную книгу")
                .isEqualTo(existsBook);
    }

}
