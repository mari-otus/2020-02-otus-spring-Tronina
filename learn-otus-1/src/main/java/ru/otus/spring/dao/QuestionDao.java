package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.util.List;

/**
 * DAO для работы с вопросами для теста.
 *
 * @author Mariya Tronina mariya.tronina@rtlabs.ru
 * @since 03.03.2020
 */
public interface QuestionDao {

    /**
     * Возвращает список вопросов для тестирования.
     *
     * @return список вопросов
     */
    List<Question> findAllQuestion();
}
