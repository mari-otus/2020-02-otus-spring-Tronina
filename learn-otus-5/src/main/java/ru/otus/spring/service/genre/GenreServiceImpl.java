package ru.otus.spring.service.genre;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.genre.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.List;

/**
 * Реализация сервиса для работы с жанрами.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    /**
     * DAO для работы с жанрами.
     */
    private final GenreDao genreDao;

    @Override
    public void addGenres(final List<Genre> genres) {
        if (CollectionUtils.isNotEmpty(genres)) {
            genreDao.insert(genres);
        }
    }
}
