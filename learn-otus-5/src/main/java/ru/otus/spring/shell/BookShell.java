package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.BookService;

import java.util.Arrays;
import java.util.List;
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

    @ShellMethod(key = "book-count", value = "Выводит общее количесвто книг")
    public void bookCount() {
        int countBook = bookService.getCountBook();
        System.out.println("Всего книг: " + countBook);
    }

    @ShellMethod(key = "book-all", value = "Выводит список всех книг")
    public void bookAll() {
        List<Book> books = bookService.getAllBook();
        books.forEach(System.out::println);
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
        long bookId = bookService.addBook(book);
        System.out.println("Книга добавлена с идентификатором: " + bookId
                           + "\n" + bookService.getBook(bookId));
    }

}
