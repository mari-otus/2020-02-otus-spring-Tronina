package ru.otus.spring.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.comment.CommentBookRepository;

import java.util.List;

/**
 * Реализация сервиса для работы с комментариями.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@Repository
public class CommentBookServiceImpl implements CommentBookService {

    /**
     * Репозиторий для работы с комментариями книг.
     */
    private final CommentBookRepository commentBookRepository;

    @Override
    public void addComment(final Comment comment) {
        commentBookRepository.save(comment);
    }

    @Override
    public List<Comment> getAllCommentByBook(final long bookId) {
        return commentBookRepository.getAllByBookId(bookId);
    }

    @Override
    public boolean deleteComment(long commentId) {
        return commentBookRepository.deleteById(commentId);
    }
}
