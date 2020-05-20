package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.otus.spring.repository.annotation.CascadeDel;

/**
 * Комментарий к книге.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("comments-book")
public class Comment {

    /**
     * Идентификатор комментария.
     */
    @Id
    private String id;
    /**
     * Комментарий.
     */
    @Field(name = "comment")
    private String comment;
    /**
     * Книга.
     */
    @CascadeDel
    @DBRef
    private Book book;
}
