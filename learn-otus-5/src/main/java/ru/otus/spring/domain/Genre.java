package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsExclude;

/**
 * Жанр произведения.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    /**
     * Идентификатор жанра.
     */
    @EqualsExclude
    private Long id;
    /**
     * Наименование жанра.
     */
    private String name;
}
