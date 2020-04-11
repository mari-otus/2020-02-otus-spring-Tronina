package ru.otus.spring.repository.comment;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Comment;

import javax.persistence.*;
import java.util.List;

/**
 * JPA-реализация репозитория для работы с комментариями.
 *
 * @author Mariya Tronina
 */
@Transactional
@Repository
public class CommentBookRepositoryJpa implements CommentBookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment save(final Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public List<Comment> getAllByBookId(final long bookId) {
        TypedQuery<Comment> query = em.createQuery(
                "select с from Comment с where с.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public boolean deleteById(long commentId) {
        Query query = em.createQuery("delete from Comment с where с.id = :id");
        query.setParameter("id", commentId);
        int cntRowDelete = query.executeUpdate();
        return cntRowDelete > 0;
    }
}
