package ru.otus.spring.service.author;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.author.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.List;

/**
 * Реализация сервиса для работы с авторами.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    /**
     * DAO для работы с авторами.
     */
    private final AuthorDao authorDao;

    @Override
    public void addAuthors(final List<Author> authors) {
        if (CollectionUtils.isNotEmpty(authors)) {
            authorDao.insert(authors);
        }
    }
}
