package ru.otus.spring.shell;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.configuration.MongockConfig;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exception.BookNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование интерактивного терминала для работы с книгами и комментариями.
 *
 * @author Mariya Tronina
 */
@Ignore
@DisplayName("Терминал LibraryShell для работы с книгами и комментариями должен")
@SpringBootTest
@Import(MongockConfig.class)
class LibraryShellTest {

    public static final String COMMAND_BOOK_ADD_COMMENT = "book-add-comment comment COMMENT id %s";
    public static final String COMMAND_BOOK_ALL_COMMENT = "book-all-comment id %s";
    public static final String COMMAND_BOOK_DEL_COMMENT = "book-del-comment id %s";
    public static final String COMMAND_BOOK_DEL = "book-del id %s";
    public static final String COMMAND_BOOK_GET = "book-get id %s";
    public static final String COMMAND_BOOK_ADD = "book-add -n BOOK -ye 2000 -g GENRE1 - GENRE2 -a AUTHOR1 AUTHOR2 -";
    public static final String COMMAND_BOOK_ALL = "book-all";

    private static List<Book> BOOK_LIST = new ArrayList<>();
    private static List<String> COMMENT_LIST = new ArrayList<>();
    private static Book NEW_BOOK;
    private static Comment NEW_COMMENT;
    private static String NOT_EXIST_ID = "100";
    private static String BOOK_ID_DEFAULT = "7";
    private static String BOOK_ID_ADD_COMMENT = "6";
    private static String COMMENT_ID_DEL = "1";

    @Autowired
    private Shell shell;

    @BeforeAll
    static void setUp() {
        BOOK_LIST = Arrays.asList(
                Book.builder().id("1").name("Война и мир")
                        .yearEdition(1981)
                        .authors(Arrays.asList(Author.builder().id("1").fio("Толстой Лев Николаевич").build()))
                        .genres(Arrays.asList(Genre.builder().id("1").name("Роман").build()))
                        .build(),
                Book.builder().id("2").name("Хаджи-Мурат")
                        .yearEdition(1975)
                        .authors(Arrays.asList(Author.builder().id("1").fio("Толстой Лев Николаевич").build()))
                        .genres(Arrays.asList(Genre.builder().id("3").name("Повесть").build()))
                        .build(),
                Book.builder().id("3").name("Евгений Онегин")
                        .yearEdition(1987)
                        .authors(Arrays.asList(Author.builder().id("2").fio("Пушкин Александр Сергеевич").build()))
                        .genres(Arrays.asList(Genre.builder().id("1").name("Роман").build(), Genre.builder().id("4").name("Стихотворение").build()))
                        .build(),
                Book.builder().id("4").name("Сказка о рыбаке и рыбке")
                        .yearEdition(2015)
                        .authors(Arrays.asList(Author.builder().id("2").fio("Пушкин Александр Сергеевич").build()))
                        .genres(Arrays.asList(Genre.builder().id("4").name("Стихотворение").build(), Genre.builder().id("5").name("Сказка").build()))
                        .build(),
                Book.builder().id("5").name("Песнь о вещем Олеге")
                        .yearEdition(2015)
                        .authors(Arrays.asList(Author.builder().id("2").fio("Пушкин Александр Сергеевич").build()))
                        .genres(Arrays.asList(Genre.builder().id("3").name("Повесть").build(), Genre.builder().id("4").name("Стихотворение").build()))
                        .build(),
                Book.builder().id("6").name("Полное собрание повестей и рассказов о Шерлоке Холмсе в одном томе")
                        .yearEdition(1998)
                        .authors(Arrays.asList(Author.builder().id("3").fio("Дойл Артур Конан").build()))
                        .genres(Arrays.asList(Genre.builder().id("2").name("Рассказ").build(), Genre.builder().id("3").name("Повесть").build()))
                        .build(),
                Book.builder().id("7").name("Улитка на склоне")
                        .yearEdition(2001)
                        .authors(Arrays.asList(Author.builder().id("4").fio("Стругацкий Аркадий Натанович").build(), Author.builder().id("5").fio("Стругацкий Борис Натанович").build()))
                        .genres(Arrays.asList(Genre.builder().id("1").name("Роман").build(), Genre.builder().id("8").name("Фантастика").build()))
                        .build(),
                Book.builder().id("8").name("Понедельник начинается в субботу")
                        .yearEdition(2001)
                        .authors(Arrays.asList(Author.builder().id("4").fio("Стругацкий Аркадий Натанович").build(), Author.builder().id("5").fio("Стругацкий Борис Натанович").build()))
                        .genres(Arrays.asList(Genre.builder().id("3").name("Повесть").build(), Genre.builder().id("8").name("Фантастика").build(), Genre.builder().id("9").name("Юмор").build()))
                        .build()
        );

        NEW_BOOK = Book.builder().id("9").name("BOOK")
                .yearEdition(2000)
                .authors(Arrays.asList(Author.builder().fio("AUTHOR1").build(), Author.builder().fio("AUTHOR2").build()))
                .genres(Arrays.asList(Genre.builder().name("GENRE1").build(), Genre.builder().name("GENRE2").build()))
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
        String delResult = (String) shell.evaluate(() ->
                String.format(COMMAND_BOOK_DEL, NOT_EXIST_ID));
        assertThat(delResult)
                .isNotNull()
                .isEqualTo("Книга удалена");
    }

    @DisplayName("Добавлять книгу")
    @Test
    void bookAdd() {
        Book book = (Book) shell.evaluate(() -> COMMAND_BOOK_ADD);

        assertThat(book)
                .isNotNull()
                .isEqualToIgnoringGivenFields(NEW_BOOK, "id");
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
        BookNotFoundException comment = (BookNotFoundException) shell.evaluate(() ->
                String.format(COMMAND_BOOK_ADD_COMMENT, NOT_EXIST_ID));
        assertThat(comment)
                .isNotNull()
                .extracting("detailMessage")
                .isEqualTo("Книга не найдена");
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
        String delResult = (String) shell.evaluate(() ->
                String.format(COMMAND_BOOK_DEL_COMMENT, NOT_EXIST_ID));
        assertThat(delResult)
                .isNotNull()
                .isEqualTo("Комментарий удален");
    }

}
