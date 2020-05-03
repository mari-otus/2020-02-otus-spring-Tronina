package ru.otus.spring.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.exception.CommentBookAddException;
import ru.otus.spring.exception.CommentBookRemoveException;
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
        try {
            commentBookRepository.save(comment);
        } catch (Exception e) {
            throw new CommentBookAddException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllComment(long bookId) {
        Book book = bookService.getBook(bookId);
        List<Comment> comments = book.getComments();
        System.out.println("Комментарии к книге: " + book);
        System.out.println("Всего комментариев: " + comments.size());
        return comments;
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        try {
            commentBookRepository.deleteById(commentId);
        } catch (Exception e) {
            throw new CommentBookRemoveException();
        }
    }
}
