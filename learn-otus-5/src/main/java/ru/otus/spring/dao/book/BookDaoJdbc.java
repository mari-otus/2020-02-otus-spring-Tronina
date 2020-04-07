package ru.otus.spring.dao.book;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.mapper.BookExtractor;
import ru.otus.spring.mapper.BookRowMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * JDBC-реализация DAO для работы с книгами.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long insert(final Book book) {
        Map<String, Object> param = new HashMap() {{
           put("name", book.getName());
           put("yearEdition", book.getYearEdition());
        }};
        jdbc.update("insert into book (name, year_edition) values (:name, :yearEdition)", param);
        long newBookId = jdbc.queryForObject("select max(id) from book where name = :name and year_edition = :yearEdition", param, Long.class);

        if (CollectionUtils.isNotEmpty(book.getAuthors())) {
            final List<Map<String, Object>> mapList = new ArrayList<>();
            book.getAuthors().forEach(author ->
                    mapList.add(
                            new HashMap() {{
                                put("book_id", newBookId);
                                put("fio", author.getFio());
                            }}
                    )
            );
            jdbc.batchUpdate("insert into book_author (book_id, author_id)  " +
                             "select :book_id, id from author " +
                             "where upper(fio) = upper(:fio)",
                    mapList.toArray(new Map[book.getAuthors().size()]));
        }

        if (CollectionUtils.isNotEmpty(book.getGenres())) {
            final List<Map<String, Object>> mapList = new ArrayList<>();
            book.getGenres().forEach(genre ->
                    mapList.add(
                            new HashMap() {{
                                put("book_id", newBookId);
                                put("genre_name", genre.getName());
                            }}
                    )
            );
            jdbc.batchUpdate("insert into book_genre (book_id, genre_id)  " +
                             "select :book_id, id from genre " +
                             "where upper(name) = upper(:genre_name)",
                    mapList.toArray(new Map[book.getGenres().size()]));
        }

        return newBookId;
    }

    @Override
    public Optional<Book> getById(final long id) {
        Map<String, Object> param = Collections.singletonMap("id", id);
        try {
            return Optional.of(jdbc.queryForObject("select " +
                                                   "b.id, b.name, b.year_edition, " +
                                                   "a.id as author_id, a.fio, " +
                                                   "g.id as genre_id, g.name as genre_name " +
                                                   "from book b " +
                                                   "left join book_author ba on (ba.book_id = b.id) " +
                                                   "left join author a on (ba.author_id = a.id) " +
                                                   "left join book_genre bg on (bg.book_id = b.id) " +
                                                   "left join genre g on (bg.genre_id = g.id) " +
                                                   "where b.id = :id", param, new BookRowMapper()));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select " +
                          "b.id, b.name, b.year_edition, " +
                          "a.id as author_id, a.fio, " +
                          "g.id as genre_id, g.name as genre_name " +
                          "from book b " +
                          "left join book_author ba on (ba.book_id = b.id) " +
                          "left join author a on (ba.author_id = a.id) " +
                          "left join book_genre bg on (bg.book_id = b.id) " +
                          "left join genre g on (bg.genre_id = g.id)",
                new BookExtractor());
    }

    @Override
    public boolean deleteById(final long id) {
        Map<String, Object> param = Collections.singletonMap("id", id);
        int rowUpdate = jdbc.update("delete from book where id = :id", param);
        return rowUpdate > 0;
    }
}
