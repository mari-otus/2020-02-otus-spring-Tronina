package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.book.BookService;
import ru.otus.spring.service.comment.CommentBookService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Интерактивный терминал для работы с книгами.
 *
 * @author Mariya Tronina
 */

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    /**
     * Сервис для работы со списком вопросов.
     */
    private final BookService bookService;

    private final CommentBookService commentBookService;

    @ShellMethod(key = "book-all", value = "Выводит список всех книг")
    public void bookAll() {
        List<Book> books = bookService.getAllBook();
        books.forEach(System.out::println);
        System.out.println("Всего книг: " + books.size());
    }

    @ShellMethod(key = "book-get", value = "Выводит книгу по идентификатору")
    public void bookGet(@ShellOption({"id"}) long bookId) {
        Book book = bookService.getBook(bookId);
        System.out.println(book);
    }

    @ShellMethod(key = "book-del", value = "Удаляет книгу по идентификатору")
    public void bookDel(@ShellOption({"id"}) long bookId) {
        boolean del = bookService.deleteBook(bookId);
        System.out.println(del ? "Книга удалена" : "Книга не может быть удалена");
    }

    @ShellMethod(key = "book-add", value = "Добавляет книгу")
    public void bookAdd(
            @ShellOption({"-name", "-n"}) String name,
            @ShellOption(value={"-ye"}, help="год издания книги") int yearEdition,
            @ShellOption(value={"-genre", "-g"}, arity = 3, help="жанры; три значения; символ '-', если значение не задано") String[] genre,
            @ShellOption(value={"-author", "-a"}, arity = 3, help="авторы; три значения; символ '-', если значение не задано") String[] author) {
        Set<Genre> genres = Arrays.asList(genre)
                                  .stream()
                                  .filter(genreName -> !genreName.equals("-"))
                                  .map(genreName -> Genre.builder()
                                                          .name(genreName)
                                                          .build())
                                  .collect(Collectors.toSet());
        Set<Author> authors = Arrays.asList(author)
                                     .stream()
                                     .filter(authorFIO -> !authorFIO.equals("-"))
                                     .map(authorFIO -> Author.builder()
                                                             .fio(authorFIO)
                                                             .build())
                                     .collect(Collectors.toSet());
        Book book = Book.builder()
                        .name(name)
                        .yearEdition(yearEdition)
                        .genres(genres)
                        .authors(authors)
                        .build();
        Book bookSaved = bookService.addBook(book);
        System.out.println("Книга добавлена с идентификатором: " + bookSaved.getId()
                           + "\n" + bookService.getBook(bookSaved.getId()));
    }

    @ShellMethod(key = "book-add-comment", value = "Добавляет комментарий к книге")
    public void bookAddComment(@ShellOption({"comment", "c"}) String comment,
                               @ShellOption({"id"}) long bookId) {
        Comment commentBook = Comment.builder()
                                     .comment(comment)
                                     .book(Book.builder()
                                               .id(bookId)
                                               .build())
                                     .build();
        commentBookService.addComment(commentBook);
    }

    @ShellMethod(key = "book-all-comment", value = "Выводит все комментарии для указанной книги")
    public void bookGetAllComment(@ShellOption({"id"}) long bookId) {
        List<Comment> comments = commentBookService.getAllCommentByBook(bookId);
        System.out.println("Комментарии к книге: " + bookService.getBook(bookId));
        System.out.println("Всего комментариев: " + comments.size());
        comments.forEach(comment ->
                System.out.println(String.format("%d. %s", comment.getId(), comment.getComment())));
    }

    @ShellMethod(key = "book-del-comment", value = "Удаляет комментарий")
    public void bookDelComment(@ShellOption({"id"}) long commentId) {
        boolean del = commentBookService.deleteComment(commentId);
        System.out.println(del ? "Комментарий удален" : "Комментарий не может быть удален");
    }

}
