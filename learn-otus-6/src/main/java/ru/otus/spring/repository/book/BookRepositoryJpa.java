package ru.otus.spring.repository.book;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * JPA-реализация репозитория для работы с книгами.
 *
 * @author Mariya Tronina
 */
@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Book save(final Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getById(final long bookId) {
        return Optional.ofNullable(em.find(Book.class, bookId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b",
                Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        return query.getResultList();
    }

    @Transactional
    @Override
    public boolean deleteById(final long bookId) {
        Book book = em.find(Book.class, bookId);
        if (book != null) {
            em.remove(book);
            return true;
        }
        return false;
    }

}
