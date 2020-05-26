package ru.otus.spring.exception.comment;

import ru.otus.spring.exception.ApplicationException;

/**
 * Исключение, если комментарий не может быть добавлен.
 *
 * @author Mariya Tronina
 */
public class CommentBookAddException extends ApplicationException {

    private static final String COMMET_BOOK_ADD_MESSAGE = "Комментарий не может быть добавлен";

    public CommentBookAddException() {
        super(COMMET_BOOK_ADD_MESSAGE);
    }
}
