package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

/**
 * Сервис для работы с книгами.
 *
 * @author Mariya Tronina
 */
public interface BookService {

    /**
     * Добавляет книгу.
     *
     * @param book книга
     * @return идентификатор книги
     */
    long addBook(Book book);

    /**
     * Возвращает книгу по идентификатору.
     *
     * @param bookId идентификатор книги
     * @return книга
     */
    Book getBook(long bookId);

    /**
     * Возвращает список всех книг.
     *
     * @return список книг
     */
    List<Book> getAllBook();

    /**
     * Возвращает общее количесвто книг.
     *
     * @return количесвто книг
     */
    int getCountBook();

    /**
     * Удаляет книгу по идентификатору.
     *
     * @param bookId идентификатор книги
     * @return true - если книга была удалена, иначе false
     */
    boolean deleteBook(long bookId);
}
