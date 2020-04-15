package ru.otus.spring.repository.comment;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * JPA-реализация репозитория для работы с комментариями.
 *
 * @author Mariya Tronina
 */
@Repository
public class CommentBookRepositoryJpa implements CommentBookRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Comment save(final Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllByBookId(final long bookId) {
        TypedQuery<Comment> query = em.createQuery(
                "select с from Comment с where с.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Transactional
    @Override
    public boolean deleteById(long commentId) {
        Comment comment = em.find(Comment.class, commentId);
        if (comment != null) {
            em.remove(comment);
            return true;
        }
        return false;
    }
}
