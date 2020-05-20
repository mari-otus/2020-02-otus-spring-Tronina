package ru.otus.spring.repository.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Book;

import java.util.List;

/**
 * Репозиторий для работы с книгами.
 *
 * @author Mariya Tronina
 */
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAllByOrderByIdAsc();

}
