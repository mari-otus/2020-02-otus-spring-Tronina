package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.author.AuthorDaoJdbc;
import ru.otus.spring.domain.Author;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование DAO JDBC для работы с авторами.
 *
 * @author Mariya Tronina
 */
@DisplayName("AuthorDao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoTest {

    @Autowired
    AuthorDaoJdbc authorDao;

    @DisplayName("добавлять список авторов в БД и только тех авторов из списка, которых нет в БД")
    @Test
    public void shouldInsertListAuthorAndNotInsertExistsAuthor() {
        List<Author> authors = Arrays.asList(
                Author.builder()
                      .fio("Толстой Лев Николаевич")
                      .build(),
                Author.builder()
                      .fio("Иванов Иван")
                      .build(),
                Author.builder()
                      .fio("Петров Петр")
                      .build());
        int[] rowInsert = authorDao.insert(authors);
        assertThat(rowInsert).isNotEmpty()
                             .isEqualTo(new int[]{0, 1, 1});
    }
}
