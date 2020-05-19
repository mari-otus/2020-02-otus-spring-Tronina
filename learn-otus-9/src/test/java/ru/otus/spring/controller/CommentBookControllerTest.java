package ru.otus.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import ru.otus.spring.dto.CommentDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование контроллера для работы с комментариями.
 *
 * @author Mariya Tronina
 */
@DisplayName("Контроллер CommentBookController должен")
@Sql(value = { "classpath:clearData.sql", "classpath:data.sql" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentBookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();
    private static final HttpEntity REQUEST_HTTP_ENTITY = new HttpEntity(HTTP_HEADERS);

    private static final int COMMENT_COUNT = 3;
    private static final List<CommentDto> COMMENT_LIST = new ArrayList<>();
    private static final Long BOOK_ID_DEFAULT = 7L;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/library";

        HTTP_HEADERS.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));

        COMMENT_LIST.clear();
        COMMENT_LIST.addAll(Arrays.asList(
                CommentDto.builder()
                        .id(1L)
                        .comment("превосходно").build(),
                CommentDto.builder()
                        .id(2L)
                        .comment("нормально").build(),
                CommentDto.builder()
                        .id(3L)
                        .comment("класс!!!").build()
        ));
    }

    @DisplayName("возвращать все комментарии книги")
    @Test
    void shouldGetBookComments() {
        final ResponseEntity<List<CommentDto>> result = this.restTemplate
                .exchange(baseUrl + "/books/{id}/comments",
                        HttpMethod.GET,
                        REQUEST_HTTP_ENTITY,
                        new ParameterizedTypeReference<List<CommentDto>>() {
                        },
                        BOOK_ID_DEFAULT);

        assertThat(result.getStatusCode())
                .as("Статус ответа должен быть 200 OK")
                .isEqualTo(HttpStatus.OK);

        assertThat(result.getBody())
                .as("Ответ не должен быть пустым")
                .isNotEmpty()
                .as("Ответ должен содержать определенное количество комментариев")
                .hasSize(COMMENT_COUNT)
                .as("Ответ должен содержать все указанные комментарии")
                .containsExactlyInAnyOrderElementsOf(COMMENT_LIST);
    }
}
