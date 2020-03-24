package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для запуска тестирования.
 *
 * @author Mariya Tronina
 */

@ShellComponent
@RequiredArgsConstructor
public class QuestionaryShell {

    /**
     * Сервис для работы со списком вопросов.
     */
    private final QuestionService questionService;
    /**
     * Список вопросов.
     */
    private List<Question> questionList = new ArrayList();
    /**
     * Имя участника тестирования.
     */
    private String username;
    /**
     * Счетчик заданных вопросов.
     */
    private int countQuestion = 0;
    /**
     * Счетчик введенных ответов.
     */
    private int countAnswer = 0;

    @ShellMethod(key = "login", value = "Ваше имя")
    public void login(@ShellOption({"username", "u"}) String username) {
        initTest(username);
    }

    @ShellMethod(key = "ask", value = "без параметров")
    public void ask() {
        if (CollectionUtils.size(questionList) == countQuestion) {
            destroyTest();
        } else {
            System.out.println(questionList.get(countQuestion).getQuestion());
            countQuestion++;
        }
    }

    @ShellMethod(key = "answer", value = "ответ")
    public void answer(String answer) {
        System.out.println("Ваш ответ: " + answer);
        countAnswer++;
    }

    @ShellMethodAvailability("ask")
    public Availability availabilityAsk() {
        return StringUtils.isNotEmpty(username)
               ? (countQuestion == countAnswer
                  ? Availability.available()
                  : Availability.unavailable("необходимо ввести ответ командой answer ANSWER"))
               : Availability.unavailable("сначала необходимо ввести Ваше имя");
    }

    @ShellMethodAvailability("answer")
    public Availability availabilityAnswer() {
        return StringUtils.isNotEmpty(username)
               ? (countQuestion == countAnswer + 1
                  ? Availability.available()
                  : Availability.unavailable("необходимо сгенерировать вопрос командой ask"))
               : Availability.unavailable("сначала необходимо ввести Ваше имя");
    }

    @ShellMethodAvailability("login")
    public Availability availabilityLogin() {
        return StringUtils.isEmpty(username)
               ? Availability.available()
               : Availability.unavailable("Вы уже авторизовались под имененм " + username
                                          + ". Завершите тестирование");
    }

    private void initTest(String username) {
        this.username = username;
        this.questionList = questionService.getQuestionList();
        System.out.println("Привет, " + username);
    }

    private void destroyTest() {
        System.out.println("тест завершен");
        this.username = null;
        this.countQuestion = 0;
        this.countAnswer = 0;
    }

}
