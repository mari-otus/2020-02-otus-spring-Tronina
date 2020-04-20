package ru.otus.spring.shell;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exception.BookNotFoundException;
import ru.otus.spring.exception.BookRemoveException;
import ru.otus.spring.exception.CommentBookAddException;
import ru.otus.spring.exception.CommentBookRemoveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование интерактивного терминала для работы с книгами и комментариями.
 *
 * @author Mariya Tronina
 */
@DisplayName("Терминал LibraryShell для работы с книгами и комментариями должен")
@SpringBootTest
class LibraryShellTest {

    public static final String COMMAND_BOOK_ADD_COMMENT = "book-add-comment comment COMMENT id %d";
    public static final String COMMAND_BOOK_ALL_COMMENT = "book-all-comment id %d";
    public static final String COMMAND_BOOK_DEL_COMMENT = "book-del-comment id %d";
    public static final String COMMAND_BOOK_DEL = "book-del id %d";
    public static final String COMMAND_BOOK_GET = "book-get id %d";
    public static final String COMMAND_BOOK_ADD = "book-add -n BOOK -ye 2000 -g GENRE1 - GENRE2 -a AUTHOR1 AUTHOR2 -";
    public static final String COMMAND_BOOK_ALL = "book-all";

    private static List<Book> BOOK_LIST = new ArrayList<>();
    private static List<String> COMMENT_LIST = new ArrayList<>();
    private static Book NEW_BOOK;
    private static Comment NEW_COMMENT;
    private static Long NOT_EXIST_ID = 100L;
    private static Long BOOK_ID_DEFAULT = 7L;
    private static Long BOOK_ID_ADD_COMMENT = 6L;
    private static Long COMMENT_ID_DEL = 1L;

    @Autowired
    private Shell shell;

    @BeforeAll
    static void setUp() {
        BOOK_LIST = Arrays.asList(
                Book.builder().id(1L).name("Война и мир")
                        .yearEdition(1981)
                        .authors(Set.of(Author.builder().id(1L).fio("Толстой Лев Николаевич").build()))
                        .genres(Set.of(Genre.builder().id(1L).name("Роман").build()))
                        .build(),
                Book.builder().id(2L).name("Хаджи-Мурат")
                        .yearEdition(1975)
                        .authors(Set.of(Author.builder().id(1L).fio("Толстой Лев Николаевич").build()))
                        .genres(Set.of(Genre.builder().id(3L).name("Повесть").build()))
                        .build(),
                Book.builder().id(3L).name("Евгений Онегин")
                        .yearEdition(1987)
                        .authors(Set.of(Author.builder().id(2L).fio("Пушкин Александр Сергеевич").build()))
                        .genres(Set.of(Genre.builder().id(1L).name("Роман").build(), Genre.builder().id(4L).name("Стихотворение").build()))
                        .build(),
                Book.builder().id(4L).name("Сказка о рыбаке и рыбке")
                        .yearEdition(2015)
                        .authors(Set.of(Author.builder().id(2L).fio("Пушкин Александр Сергеевич").build()))
                        .genres(Set.of(Genre.builder().id(4L).name("Стихотворение").build(), Genre.builder().id(5L).name("Сказка").build()))
                        .build(),
                Book.builder().id(5L).name("Песнь о вещем Олеге")
                        .yearEdition(2015)
                        .authors(Set.of(Author.builder().id(2L).fio("Пушкин Александр Сергеевич").build()))
                        .genres(Set.of(Genre.builder().id(3L).name("Повесть").build(), Genre.builder().id(4L).name("Стихотворение").build()))
                        .build(),
                Book.builder().id(6L).name("Полное собрание повестей и рассказов о Шерлоке Холмсе в одном томе")
                        .yearEdition(1998)
                        .authors(Set.of(Author.builder().id(3L).fio("Дойл Артур Конан").build()))
                        .genres(Set.of(Genre.builder().id(2L).name("Рассказ").build(), Genre.builder().id(3L).name("Повесть").build()))
                        .build(),
                Book.builder().id(7L).name("Улитка на склоне")
                        .yearEdition(2001)
                        .authors(Set.of(Author.builder().id(4L).fio("Стругацкий Аркадий Натанович").build(), Author.builder().id(5L).fio("Стругацкий Борис Натанович").build()))
                        .genres(Set.of(Genre.builder().id(1L).name("Роман").build(), Genre.builder().id(8L).name("Фантастика").build()))
                        .build(),
                Book.builder().id(8L).name("Понедельник начинается в субботу")
                        .yearEdition(2001)
                        .authors(Set.of(Author.builder().id(4L).fio("Стругацкий Аркадий Натанович").build(), Author.builder().id(5L).fio("Стругацкий Борис Натанович").build()))
                        .genres(Set.of(Genre.builder().id(3L).name("Повесть").build(), Genre.builder().id(8L).name("Фантастика").build(), Genre.builder().id(9L).name("Юмор").build()))
                        .build()
        );

        NEW_BOOK = Book.builder().id(9L).name("BOOK")
                .yearEdition(2000)
                .authors(Set.of(Author.builder().fio("AUTHOR1").build(), Author.builder().fio("AUTHOR2").build()))
                .genres(Set.of(Genre.builder().name("GENRE1").build(), Genre.builder().name("GENRE2").build()))
                .build();

        NEW_COMMENT = Comment.builder()
                .comment("COMMENT")
                .book(Book.builder().id(BOOK_ID_ADD_COMMENT).build())
                .build();

        COMMENT_LIST = Arrays.asList(
                String.format("%d. %s", 1, "превосходно"),
                String.format("%d. %s", 2, "нормально"),
                String.format("%d. %s", 3, "класс!!!")
                );
    }

    @DisplayName("Выводить список всех книг")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void bookAll() {
        List<Book> res = (List<Book>) shell.evaluate(() -> COMMAND_BOOK_ALL);
        assertThat(res)
                .isNotEmpty()
                .hasSize(8)
                .isEqualTo(BOOK_LIST);
    }

    @DisplayName("Выводить книгу по идентификатору")
    @Test
    void bookGet() {
        Book book = (Book) shell.evaluate(() ->
                String.format(COMMAND_BOOK_GET, BOOK_ID_DEFAULT));
        assertThat(book)
                .isNotNull()
                .isEqualTo(BOOK_LIST.get(6));
    }

    @DisplayName("Удалять существующую книгу по идентификатору")
    @Test
    void bookDelShouldSuccess() {
        String delResult = (String) shell.evaluate(() ->
                String.format(COMMAND_BOOK_DEL, BOOK_ID_DEFAULT));
        assertThat(delResult)
                .isNotNull()
                .isEqualTo("Книга удалена");
    }

    @DisplayName("Удалять несуществующую книгу по идентификатору")
    @Test
    void bookDelShouldFail() {
        BookRemoveException delResult = (BookRemoveException) shell.evaluate(() ->
                String.format(COMMAND_BOOK_DEL, NOT_EXIST_ID));
        assertThat(delResult)
                .isNotNull()
                .extracting("detailMessage")
                .isEqualTo("Книга не может быть удалена");
    }

    @DisplayName("Добавлять книгу")
    @Test
    void bookAdd() {
        Book book = (Book) shell.evaluate(() -> COMMAND_BOOK_ADD);
        book.setAuthors(book.getAuthors().stream().map(author -> {
            author.setId(null);
            return author;
        }).collect(Collectors.toSet()));
        book.setGenres(book.getGenres().stream().map(genre -> {
            genre.setId(null);
            return genre;
        }).collect(Collectors.toSet()));

        assertThat(book)
                .isNotNull()
        .isEqualTo(NEW_BOOK);
    }

    @DisplayName("Добавлять комментарий к книге")
    @Test
    void bookAddComment() {
        Comment comment = (Comment) shell.evaluate(() ->
                String.format(COMMAND_BOOK_ADD_COMMENT, BOOK_ID_ADD_COMMENT));
        assertThat(comment)
                .isNotNull()
                .isEqualToComparingOnlyGivenFields(NEW_COMMENT, "comment", "book.id");
    }

    @DisplayName("Обработать запрос на добавление комментария к несуществующей книге")
    @Test
    void bookNoExistAddCommentShouldFail() {
        CommentBookAddException comment = (CommentBookAddException) shell.evaluate(() ->
                String.format(COMMAND_BOOK_ADD_COMMENT, NOT_EXIST_ID));
        assertThat(comment)
                .isNotNull()
                .extracting("detailMessage")
                .isEqualTo("Комментарий не может быть добавлен");
    }

    @DisplayName("Выводить все комментарии для существующей книги")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void bookAllComment() {
        List<String> comments = (List<String>) shell.evaluate(() ->
                String.format(COMMAND_BOOK_ALL_COMMENT, BOOK_ID_DEFAULT));
        assertThat(comments)
                .isNotNull()
                .hasSize(3)
                .isEqualTo(COMMENT_LIST);
    }

    @DisplayName("Обработать случай запроса на вывод всех комментариев для несуществующей книги")
    @Test
    void bookNotExistAllCommentShouldFail() {
        BookNotFoundException comments = (BookNotFoundException) shell.evaluate(() ->
                String.format(COMMAND_BOOK_ALL_COMMENT, NOT_EXIST_ID));
        assertThat(comments)
                .isNotNull()
                .extracting("detailMessage")
                .isEqualTo("Книга не найдена");
    }

    @DisplayName("Удалять комментарий")
    @Test
    void bookDelCommentShouldSuccess() {
        String delResult = (String) shell.evaluate(() ->
                String.format(COMMAND_BOOK_DEL_COMMENT, COMMENT_ID_DEL));
        assertThat(delResult)
                .isNotNull()
                .isEqualTo("Комментарий удален");
    }

    @DisplayName("Удалять несуществующий комментарий")
    @Test
    void bookDelCommentShouldFail() {
        CommentBookRemoveException delResult = (CommentBookRemoveException) shell.evaluate(() ->
                String.format(COMMAND_BOOK_DEL_COMMENT, NOT_EXIST_ID));
        assertThat(delResult)
                .isNotNull()
                .extracting("detailMessage")
                .isEqualTo("Комментарий не может быть удален");
    }

}
