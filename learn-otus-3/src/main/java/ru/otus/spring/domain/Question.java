package ru.otus.spring.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ОБъект вопроса для теста.
 *
 * @author Mariya Tronina
 * @since 03.03.2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "question", "variant", "answers" })
public class Question {

    /**
     * Вопрос теста.
     */
    private String question;
    /**
     * Количество вариантов ответа: 0 - вопрос без вариантов.
     */
    private int variant;
    /**
     * Варианты ответов, разделенные запятой.
     */
    private String answers;

}
