package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsExclude;

/**
 * Автор книги.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    /**
     * Идентификатор автора.
     */
    @EqualsExclude
    private Long id;
    /**
     * Фамилия имя отчесвто автора.
     */
    private String fio;

}
