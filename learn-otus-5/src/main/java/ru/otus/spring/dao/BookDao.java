package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с книгами.
 *
 * @author Mariya Tronina
 */
public interface BookDao {

    /**
     * Возвращает общее количесвто книг.
     *
     * @return количесвто книг
     */
    int count();

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
     * @return спсиок книг
     */
    List<Book> getAll();

    /**
     * Удаляет книгу по идентфикатору.
     *
     * @param id идентификатор книги в БД
     * @return true - крига успешно удалена, иначе false
     */
    boolean deleteById(long id);
}
