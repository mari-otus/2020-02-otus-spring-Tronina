package service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Тестирование сервиса для работы со списком вопросов.
 *
 * @author Mariya Tronina
 * @since 03.03.2020
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionServiceTest {

    @Mock
    private QuestionDao dao;
    @InjectMocks
    private QuestionServiceImpl questionService;

    private List<Question> questionList;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        questionList = new ArrayList<>();
        questionList.add(
                new Question("Вопрос 1",0, null)
        );
        questionList.add(
                new Question("Вопрос 2",2,"да,нет")
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
