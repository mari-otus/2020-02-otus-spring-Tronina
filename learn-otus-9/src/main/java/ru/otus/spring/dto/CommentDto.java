package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Комментарий к книге.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    /**
     * Идентификатор комментария.
     */
    private Long id;
    /**
     * Комментарий.
     */
    private String comment;

}
