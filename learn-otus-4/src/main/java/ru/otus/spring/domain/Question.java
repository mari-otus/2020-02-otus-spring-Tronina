package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ОБъект вопроса для теста.
 *
 * @author Mariya Tronina
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    /**
     * Вопрос теста.
     */
    private String question;

}
