package service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование сервиса для работы со списком вопросов.
 *
 * @author Mariya Tronina
 * @since 03.03.2020
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionServiceTest {

    private QuestionDao dao;
    private QuestionService questionService;
    private List<Question> questionList;

    @BeforeAll
    public void setUp() {
        dao = Mockito.mock(QuestionDao.class);
        questionService = new QuestionServiceImpl(dao);
        questionList = new ArrayList<>();
        questionList.add(
                Question.builder()
                .question("Вопрос 1")
                .variant(0)
                .build()
        );
        questionList.add(
                Question.builder()
                        .question("Вопрос 2")
                        .variant(2)
                        .answers("да,нет")
                        .build()
        );
    }

    @Test
    public void getQuestionList() {
        when(dao.findAllQuestion()).thenReturn(questionList);
        List<Question> questionList = questionService.getQuestionList();

        assertThat(questionList).isNotEmpty();
        assertThat(questionList).isEqualTo(this.questionList);
    }
}
