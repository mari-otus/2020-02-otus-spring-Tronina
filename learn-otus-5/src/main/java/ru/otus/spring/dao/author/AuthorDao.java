package ru.otus.spring.dao.author;

import ru.otus.spring.domain.Author;

import java.util.List;

/**
 * DAO для работы с авторами.
 *
 * @author Mariya Tronina
 */
public interface AuthorDao {

    /**
     * Добавляет список авторов.
     *
     * @param authors список авторов
     * @return массив, содержащий для каждого автора из списка 0 или 1;
     * 0 - автор уже существует и не добавлен в БД, 1 - автор добавлен в БД
     */
    int[] insert(List<Author> authors);
}
