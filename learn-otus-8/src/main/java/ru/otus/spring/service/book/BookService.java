package ru.otus.spring.service.book;

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
    Book addBook(Book book);

    /**
     * Возвращает книгу по идентификатору.
     *
     * @param bookId идентификатор книги
     * @return книга
     */
    Book getBook(String bookId);

    /**
     * Возвращает список всех книг.
     *
     * @return список книг
     */
    List<Book> getAllBook();

    /**
     * Удаляет книгу по идентификатору.
     *
     * @param bookId идентификатор книги
     * @return true - если книга была удалена, иначе false
     */
    void deleteBook(String bookId);
}
