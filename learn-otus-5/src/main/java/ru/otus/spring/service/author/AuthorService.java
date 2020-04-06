package ru.otus.spring.service.author;

import ru.otus.spring.domain.Author;

import java.util.List;

/**
 * Сервис для работы с авторами.
 *
 * @author Mariya Tronina
 */
public interface AuthorService {

    /**
     * Добавляет список авторов.
     *
     * @param authors список авторов
     */
    void addAuthors(List<Author> authors);
}
