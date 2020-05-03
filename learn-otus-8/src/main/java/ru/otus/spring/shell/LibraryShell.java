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
import java.util.stream.Collectors;

/**
 * Интерактивный терминал для работы с книгами и комментариями.
 *
 * @author Mariya Tronina
 */

@ShellComponent
@RequiredArgsConstructor
public class LibraryShell {

    /**
     * Сервис для работы со списком вопросов.
     */
    private final BookService bookService;

    private final CommentBookService commentBookService;

    @ShellMethod(key = "book-all", value = "Выводит список всех книг")
    public List<Book> bookAll() {
        List<Book> books = bookService.getAllBook();
        System.out.println("Всего книг: " + books.size());
        return books;
    }

    @ShellMethod(key = "book-get", value = "Выводит книгу по идентификатору")
    public Book bookGet(@ShellOption({"id"}) String bookId) {
        Book book = bookService.getBook(bookId);
        return book;
    }

    @ShellMethod(key = "book-del", value = "Удаляет книгу по идентификатору")
    public String bookDel(@ShellOption({"id"}) String bookId) {
        bookService.deleteBook(bookId);
        return "Книга удалена";
    }

    @ShellMethod(key = "book-add", value = "Добавляет книгу")
    public Book bookAdd(
            @ShellOption({"-name", "-n"}) String name,
            @ShellOption(value={"-ye"}, help="год издания книги") int yearEdition,
            @ShellOption(value={"-genre", "-g"}, arity = 3, help="жанры; три значения; символ '-', если значение не задано") String[] genre,
            @ShellOption(value={"-author", "-a"}, arity = 3, help="авторы; три значения; символ '-', если значение не задано") String[] author) {
        List<Genre> genres = Arrays.asList(genre)
                                  .stream()
                                  .filter(genreName -> !genreName.equals("-"))
                                  .map(genreName -> Genre.builder()
                                                          .name(genreName)
                                                          .build())
                                  .collect(Collectors.toList());
        List<Author> authors = Arrays.asList(author)
                                     .stream()
                                     .filter(authorFIO -> !authorFIO.equals("-"))
                                     .map(authorFIO -> Author.builder()
                                                             .fio(authorFIO)
                                                             .build())
                                     .collect(Collectors.toList());
        Book book = Book.builder()
                        .name(name)
                        .yearEdition(yearEdition)
                        .genres(genres)
                        .authors(authors)
                        .build();
        Book bookSaved = bookService.addBook(book);
        return bookSaved;
    }

    @ShellMethod(key = "book-add-comment", value = "Добавляет комментарий к книге")
    public Comment bookAddComment(@ShellOption({"comment", "c"}) String comment,
                               @ShellOption({"id"}) String bookId) {
        Comment commentBook = Comment.builder()
                                     .comment(comment)
                                     .book(Book.builder()
                                               .id(bookId)
                                               .build())
                                     .build();
        commentBookService.addComment(commentBook);
        return commentBook;
    }

    @ShellMethod(key = "book-all-comment", value = "Выводит все комментарии для указанной книги")
    public List<String> bookGetAllComment(@ShellOption({"id"}) String bookId) {
        List<Comment> comments = commentBookService.getAllComment(bookId);
        return comments.stream()
                .map(comment -> String.format("%s. %s", comment.getId(), comment.getComment()))
                .collect(Collectors.toList());
    }

    @ShellMethod(key = "book-del-comment", value = "Удаляет комментарий")
    public String bookDelComment(@ShellOption({"id"}) String commentId) {
        commentBookService.deleteComment(commentId);
        return "Комментарий удален";
    }

}
