package ru.otus.spring.repository.comment;

import ru.otus.spring.domain.Comment;

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
     * Удаляет комментарий по идентификатору.
     *
     * @param commentId идентификатор комментария в БД
     * @return true - комментарий успешно удален, иначе false
     */
    boolean deleteById(long commentId);
}
