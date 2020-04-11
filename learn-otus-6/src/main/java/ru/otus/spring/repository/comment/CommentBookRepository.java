package ru.otus.spring.repository.comment;

import ru.otus.spring.domain.Comment;

import java.util.List;

/**
 * Репозиторий для работы с комментариями книги.
 *
 * @author Mariya Tronina
 */
public interface CommentBookRepository {

    /**
     * Добавляет новый комментарий или обновляет существующий.
     *
     * @param comment комментарий
     * @return комментарий
     */
    Comment save(Comment comment);

    /**
     * Возвращает список всех комментариев у книги.
     *
     * @param bookId идентификатор книги
     * @return список комментариев
     */
    List<Comment> getAllByBookId(long bookId);

    /**
     * Удаляет комментарий по идентификатору.
     *
     * @param commentId идентификатор комментария в БД
     * @return true - комментарий успешно удален, иначе false
     */
    boolean deleteById(long commentId);
}
