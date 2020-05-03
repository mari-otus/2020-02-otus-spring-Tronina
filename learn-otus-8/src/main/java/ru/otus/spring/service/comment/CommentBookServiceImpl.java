package ru.otus.spring.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.exception.BookNotFoundException;
import ru.otus.spring.repository.comment.CommentBookRepository;
import ru.otus.spring.service.book.BookService;

import java.util.List;

/**
 * Реализация сервиса для работы с комментариями.
 *
 * @author Mariya Tronina
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CommentBookServiceImpl implements CommentBookService {

    /**
     * Сервис для работы с книгами.
     */
    private final BookService bookService;
    /**
     * Репозиторий для работы с комментариями книг.
     */
    private final CommentBookRepository commentBookRepository;

    @Transactional
    @Override
    public void addComment(final Comment comment) {
        Book book = bookService.getBook(comment.getBook().getId());
        if (book == null) {
            throw new BookNotFoundException();
        }
        commentBookRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllComment(String bookId) {
        Book book = bookService.getBook(bookId);
        return commentBookRepository.findAllByBook(book);
    }

    @Transactional
    @Override
    public void deleteComment(String commentId) {
        commentBookRepository.deleteById(commentId);
    }
}
