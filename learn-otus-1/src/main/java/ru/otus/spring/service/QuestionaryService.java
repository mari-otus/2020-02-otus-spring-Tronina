package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;

/**
 * Сервис для запуска тестирования.
 *
 * @author Mariya Tronina
 * @since 03.03.2020
 */
public interface QuestionaryService {

    /**
     * Запускает процесс тестирования.
     *
     * @param questionList список вопросов
     */
    void runQuestionary(List<Question> questionList);

}
