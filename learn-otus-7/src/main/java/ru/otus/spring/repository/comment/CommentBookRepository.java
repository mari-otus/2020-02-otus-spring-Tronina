package ru.otus.spring.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Comment;

/**
 * Репозиторий для работы с комментариями книги.
 *
 * @author Mariya Tronina
 */
public interface CommentBookRepository extends JpaRepository<Comment, Long> {

}
