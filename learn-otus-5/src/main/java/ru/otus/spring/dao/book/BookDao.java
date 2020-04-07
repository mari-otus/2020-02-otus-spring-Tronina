package ru.otus.spring.dao.book;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * DAO для работы с книгами.
 *
 * @author Mariya Tronina
 */
public interface BookDao {

    /**
     * Добавляет книгу.
     *
     * @param book книга
     * @return идентификатор книги в БД
     */
    long insert(Book book);

    /**
     * Возвращает книгу по идентификатору.
     *
     * @param id идентификатор книги в БД
     * @return книга
     */
    Optional<Book> getById(long id);

    /**
     * Возвращает список всех книг.
     *
     * @return список книг
     */
    List<Book> getAll();

    /**
     * Удаляет книгу по идентификатору.
     *
     * @param id идентификатор книги в БД
     * @return true - книга успешно удалена, иначе false
     */
    boolean deleteById(long id);
}
