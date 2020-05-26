package ru.otus.spring.exception.comment;

import ru.otus.spring.exception.ApplicationException;

/**
 * Исключение, если комментарий не может быть удален.
 *
 * @author Mariya Tronina
 */
public class CommentBookRemoveException extends ApplicationException {

    private static final String COMMET_BOOK_REMOVE_MESSAGE = "Комментарий не может быть удален";

    public CommentBookRemoveException() {
        super(COMMET_BOOK_REMOVE_MESSAGE);
    }
}
