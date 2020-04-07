package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.genre.GenreDaoJdbc;
import ru.otus.spring.domain.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование DAO JDBC для работы с жанрами.
 *
 * @Genre Mariya Tronina
 */
@DisplayName("GenreDao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoTest {

    @Autowired
    GenreDaoJdbc genreDao;

    @DisplayName("добавлять список жанров в БД и только те жанры из списка, которых нет в БД")
    @Test
    public void shouldInsertListGenreAndNotInsertExistsGenre() {
        List<Genre> genres = Arrays.asList(
                Genre.builder()
                     .name("Роман")
                     .build(),
                Genre.builder()
                     .name("Фантастика")
                     .build(),
                Genre.builder()
                     .name("Новый жанр")
                     .build());
        int[] rowInsert = genreDao.insert(genres);
        assertThat(rowInsert).isNotEmpty()
                             .isEqualTo(new int[]{0, 0, 1});
    }
}
