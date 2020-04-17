package ru.otus.spring.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.comment.CommentBookRepository;
import ru.otus.spring.service.book.BookService;

import java.util.List;

/**
 * Реализация сервиса для работы с комментариями.
 *
 * @author Mariya Tronina
 */
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

    @Override
    public void addComment(final Comment comment) {
        commentBookRepository.save(comment);
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

    @Override
    public boolean deleteComment(long commentId) {
        return commentBookRepository.deleteById(commentId);
    }
}
