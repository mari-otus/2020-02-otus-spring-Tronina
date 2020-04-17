package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@NamedEntityGraph(name = "author-genre-entity-graph",
                  attributeNodes = {
                          @NamedAttributeNode("authors"),
                          @NamedAttributeNode("genres")
                  })
public class Book {

    /**
     * Идентификатор книги.
     */
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
               fetch = FetchType.LAZY, mappedBy = "book")
    private List<Comment> comments;

}
