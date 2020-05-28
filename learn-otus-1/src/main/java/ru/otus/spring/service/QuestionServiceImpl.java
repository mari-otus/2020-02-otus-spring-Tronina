package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;

/**
 * Сервис для работы со списком вопросов.
 *
 * @author Mariya Tronina
 * @since 03.03.2020
 */
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    /**
     * DAO для работы с вопросами для теста.
     */
    private final QuestionDao dao;

    @Override
    public List<Question> getQuestionList() {
        return dao.findAllQuestion();
    }
}
