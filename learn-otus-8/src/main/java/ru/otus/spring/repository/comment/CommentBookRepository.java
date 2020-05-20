package ru.otus.spring.repository.comment;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;

/**
 * Репозиторий для работы с комментариями книги.
 *
 * @author Mariya Tronina
 */
public interface CommentBookRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBook(Book book);
}
