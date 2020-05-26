package ru.otus.spring.exception.comment;

import ru.otus.spring.exception.ApplicationException;

/**
 * Исключение, если список комментариев книги пуст.
 *
 * @author Mariya Tronina
 */
public class CommentBookListEmptyException extends ApplicationException {

    private static final String COMMENT_LIST_EMPTY_MESSAGE = "Список комментариев книги пуст";

    public CommentBookListEmptyException() {
        super(COMMENT_LIST_EMPTY_MESSAGE);
    }
}
