package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsExclude;

import javax.persistence.*;

/**
 * Автор книги.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author {

    /**
     * Идентификатор автора.
     */
    @EqualsExclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Фамилия имя отчество автора.
     */
    @Column(name = "fio", nullable = false)
    private String fio;

}
