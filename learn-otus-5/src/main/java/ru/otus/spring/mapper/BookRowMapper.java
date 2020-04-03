package ru.otus.spring.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Обработчик записи из таблицы книг.
 *
 * @author Mariya Tronina
 */
public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        Book book = Book.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name"))
            .yearEdition(resultSet.getInt("year_edition"))
            .build();
        Map<Long, Author> books = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        Map<Long, Genre> genres = new HashMap<>();
        do {
            authors.putIfAbsent(resultSet.getLong("author_id"),
                    Author.builder()
                          .id(resultSet.getLong("author_id"))
                          .fio(resultSet.getString("fio"))
                          .build());
            genres.putIfAbsent(resultSet.getLong("genre_id"),
                    Genre.builder()
                         .id(resultSet.getLong("genre_id"))
                         .name(resultSet.getString("genre_name"))
                         .build());
        } while (resultSet.next());
        book.setAuthors(authors.values().stream().collect(Collectors.toList()));
        book.setGenres(genres.values().stream().collect(Collectors.toList()));
        return book;
    }
}
