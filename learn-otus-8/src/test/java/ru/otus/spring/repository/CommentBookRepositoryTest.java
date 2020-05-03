package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.configuration.MongockConfig;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.comment.CommentBookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Тестирование Repository Mongo для работы с комментариями.
 *
 * @author Mariya Tronina
 */
@DisplayName("CommentBookRepository для работы с комментариями должно")
@DataMongoTest
@Import(MongockConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CommentBookRepositoryTest {

    public static final String DEFAULT_COMMENT_ID = "3";
    public static final String DEFAULT_BOOK_ID = "7";

    @Autowired
    private CommentBookRepository commentBookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @DisplayName("добавлять комментарий к книге в БД")
    @Test
    public void shouldInsertComment() {
        Comment newComment = Comment.builder()
                .comment("превосходно")
                .book(Book.builder().id(DEFAULT_BOOK_ID).build())
                .build();
        Comment expectedComment = commentBookRepository.save(newComment);
        Comment actualComment = mongoTemplate.findOne(Query.query(Criteria.where("id").is(expectedComment.getId())), Comment.class);

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
        assertThatCode(() -> {
            commentBookRepository.deleteById(DEFAULT_COMMENT_ID + 100);
        }).doesNotThrowAnyException();
    }

}
