package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.comment.CommentBookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Тестирование Repository JPA для работы с комментариями.
 *
 * @author Mariya Tronina
 */
@DisplayName("CommentBookRepository для работы с комментариями должно")
@Transactional
@DataJpaTest
public class CommentBookRepositoryTest {

    public static final int EXPECTED_COMMENT_COUNT = 3;
    public static final long DEFAULT_COMMENT_ID = 3L;
    public static final long DEFAULT_BOOK_ID = 7L;

    @Autowired
    private CommentBookRepository commentBookRepository;

    @Autowired
    private TestEntityManager em;


    @DisplayName("добавлять комментарий к книге в БД")
    @Test
    public void shouldInsertComment() {
        Comment newComment = Comment.builder()
                .comment("превосходно")
                .book(Book.builder().id(DEFAULT_BOOK_ID).build())
                .build();
        Comment expectedComment = commentBookRepository.save(newComment);
        Comment actualComment = em.find(Comment.class, expectedComment.getId());

        assertThat(actualComment).isNotNull();
        assertThat(actualComment.equals(expectedComment));
    }

    @DisplayName("удалять существующий комментарий книги из БД")
    @Test
    public void shouldDeleteExistsCommentBook() {
        assertThatCode(() -> {
            commentBookRepository.deleteById(DEFAULT_COMMENT_ID);
        }).doesNotThrowAnyException();
    }

    @DisplayName("удалять несуществующий комментарий книги из БД")
    @Test
    public void shouldDeleteNotExistsCommentBook() {
        assertThatThrownBy(() -> {
            commentBookRepository.deleteById(DEFAULT_COMMENT_ID * 1000);
        }).isInstanceOf(EmptyResultDataAccessException.class);
    }

}
