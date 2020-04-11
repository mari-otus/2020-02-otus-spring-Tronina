package ru.otus.spring.domain;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;

import javax.persistence.*;

/**
 * Комментарий к книге.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_book")
public class Comment {

    /**
     * Идентификатор комментария.
     */
    @EqualsExclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Комментарий.
     */
    @Column(name = "comment", nullable = false)
    private String comment;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
}
