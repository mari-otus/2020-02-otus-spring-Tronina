package ru.otus.spring.service.comment;

import ru.otus.spring.domain.Comment;

import java.util.List;

/**
 * Сервис для работы с комментариями.
 *
 * @author Mariya Tronina
 */
public interface CommentBookService {

    /**
     * Добавляет комментарий к книге.
     *
     * @param comment комментарий
     */
    void addComment(Comment comment);

    /**
     * Возвращает список всех комментариев у книги.
     *
     * @param bookId идентификатор книги
     * @return список комментариев
     */
    List<Comment> getAllComment(String bookId);

    /**
     * Удаляет комментарий по идентификатору.
     *
     * @param commentId идентификатор комментария
     */
    void deleteComment(String commentId);
}
