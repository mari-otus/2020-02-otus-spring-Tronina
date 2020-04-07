package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsExclude;

import java.util.List;

/**
 * Книга.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    /**
     * Идентификатор книги.
     */
    @EqualsExclude
    private Long id;
    /**
     * Наименование книги.
     */
    private String name;
    /**
     * Год издания книги.
     */
    private int yearEdition;
    /**
     * Список авторов книги.
     */
    private List<Author> authors;
    /**
     * Список жанров, к которым относится книга.
     */
    private List<Genre> genres;
}
