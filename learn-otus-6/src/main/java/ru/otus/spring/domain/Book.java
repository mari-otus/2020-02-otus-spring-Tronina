package ru.otus.spring.domain;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Книга.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
@NamedEntityGraph(name = "author-genre-comment-entity-graph",
                  attributeNodes = {
                          @NamedAttributeNode("authors"),
                          @NamedAttributeNode("genres"),
                          @NamedAttributeNode("comments")
                  })
public class Book {

    /**
     * Идентификатор книги.
     */
    @EqualsExclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Наименование книги.
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * Год издания книги.
     */
    @Column(name = "year_edition")
    private int yearEdition;
    /**
     * Список авторов книги.
     */
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;
    /**
     * Список жанров, к которым относится книга.
     */
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;
    /**
     * Комментарии к книге.
     */
    @ToString.Exclude
    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL,
               fetch = FetchType.EAGER, mappedBy = "book")
    private List<Comment> comments;

}
