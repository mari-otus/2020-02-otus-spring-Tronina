package ru.otus.spring.repository.book;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

/**
 * JPA-реализация репозитория для работы с книгами.
 *
 * @author Mariya Tronina
 */
@Transactional
@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(final Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> getById(final long bookId) {
        return Optional.ofNullable(em.find(Book.class, bookId));
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-genre-comment-entity-graph");
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b",
                Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        return query.getResultList();
    }

    @Override
    public boolean deleteById(final long bookId) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", bookId);
        int cntRowDelete = query.executeUpdate();
        return cntRowDelete > 0;
    }

}
