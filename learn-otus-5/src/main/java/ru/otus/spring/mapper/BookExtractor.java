package ru.otus.spring.mapper;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Обработчик выборки из таблицы книг.
 *
 * @author Mariya Tronina
 */
public class BookExtractor implements ResultSetExtractor<List<Book>> {

    @Override
    public List<Book> extractData(final ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Book> books = new HashMap<>();
        while (resultSet.next()) {
            books.putIfAbsent(resultSet.getLong("id"),
                    Book.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .yearEdition(resultSet.getInt("year_edition"))
                        .build());
            Author author = Author.builder()
                          .id(resultSet.getLong("author_id"))
                          .fio(resultSet.getString("fio"))
                          .build();
            Genre genre = Genre.builder()
                         .id(resultSet.getLong("genre_id"))
                         .name(resultSet.getString("genre_name"))
                         .build();
            Book book = books.get(resultSet.getLong("id"));
            book.setAuthors(
                    Stream.concat(
                        CollectionUtils.emptyIfNull(book.getAuthors()).stream(),
                        Arrays.asList(author).stream())
                    .distinct()
                    .collect(Collectors.toList()));
            book.setGenres(
                    Stream.concat(
                        CollectionUtils.emptyIfNull(book.getGenres()).stream(),
                        Arrays.asList(genre).stream())
                    .distinct()
                    .collect(Collectors.toList()));
        }
        return books.values().stream().collect(Collectors.toList());
    }
}
