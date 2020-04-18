package ru.otus.spring.repository.book;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с книгами.
 *
 * @author Mariya Tronina
 */
public interface BookRepository {

    /**
     * Добавляет новую книгу или обновляет существующую.
     *
     * @param book книга
     * @return книга
     */
    Book save(Book book);

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
