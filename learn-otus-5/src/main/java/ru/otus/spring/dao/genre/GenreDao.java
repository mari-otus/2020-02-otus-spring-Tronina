package ru.otus.spring.dao.genre;

import ru.otus.spring.domain.Genre;

import java.util.List;

/**
 * DAO для работы с жанрами произведений.
 *
 * @author Mariya Tronina
 */
public interface GenreDao {

    /**
     * Добавляет жанры.
     *
     * @param genres список жанров
     * @return массив, содержащий для каждого жанра из списка 0 или 1;
     * 0 - жанр уже существует и не добавлен в БД, 1 - жанр добавлен в БД
     */
    int[] insert(List<Genre> genres);
}
