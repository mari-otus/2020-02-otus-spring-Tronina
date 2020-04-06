package ru.otus.spring.dao.genre;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JDBC-реализация DAO для работы с жанрами.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int[] insert(final List<Genre> genres) {
        if (CollectionUtils.isNotEmpty(genres)) {
            final List<Map<String, Object>> mapList = new ArrayList<>();
            genres.forEach(genre ->
                    mapList.add(
                            Collections.singletonMap("genre_name", genre.getName())
                    )
            );
            return jdbc.batchUpdate("insert into genre (name) " +
                                    "select t.name from (values(:genre_name)) as t (name) " +
                                    "where not exists(select 1 from genre " +
                                    "where upper(name) = upper(t.name))",
                    mapList.toArray(new Map[genres.size()]));
        }
        return new int[0];
    }
}
