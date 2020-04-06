package ru.otus.spring.service.genre;

import ru.otus.spring.domain.Genre;

import java.util.List;

/**
 * Сервис для работы с жанрами.
 *
 * @author Mariya Tronina
 */
public interface GenreService {

    /**
     * Добавляет список жанров.
     *
     * @param genres список жанров
     */
    void addGenres(List<Genre> genres);
}
