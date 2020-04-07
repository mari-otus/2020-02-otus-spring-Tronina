package ru.otus.spring.dao.author;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JDBC-реализация DAO для работы с авторами.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int[] insert(final List<Author> authors) {
        if (CollectionUtils.isNotEmpty(authors)) {
            final List<Map<String, Object>> mapList = new ArrayList<>();
            authors.forEach(author ->
                    mapList.add(
                            Collections.singletonMap("fio", author.getFio())
                    )
            );
            return jdbc.batchUpdate("insert into author (fio) " +
                                    "select t.fio from (values(:fio)) as t (fio) " +
                                    "where not exists(select 1 from author " +
                                    "where upper(fio) = upper(t.fio))",
                    mapList.toArray(new Map[authors.size()]));
        }
        return new int[0];
    }
}
