package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Сервис для запуска тестирования.
 *
 * @author Mariya Tronina
 * @since 02.03.2020
 */

@Service
@RequiredArgsConstructor
public class QuestionaryServiceImpl implements QuestionaryService {

    /**
     * Локаль сообщений.
     */
    @Value("${locale}")
    private String locale;
    /**
     * Обработка сообщений с поддержкой параметризации и интернационализации.
     */
    private final MessageSource messageSource;
    /**
     * Сервис для работы со списком вопросов.
     */
    private final QuestionService questionService;
    /**
     * Счетчик валидных ответов.
     */
    private int countAnswer = 0;

    @Override
    public void runQuestionary() {
        List<Question> questionList = questionService.getQuestionList();
        Scanner sc = new Scanner(System.in);
        println("quest.fio");
        String fio = sc.nextLine();
        questionList.stream()
                    .forEach(
                            question -> {
                                println(question.getQuestion());
                                if (question.getVariant() > 0) {
                                    Arrays.asList(question.getAnswers().split(","))
                                          .forEach(this::println);
                                    while (!sc.hasNext("[0-"+question.getVariant()+"]+")) {
                                        println("answer.error");
                                        sc.next();
                                    }
                                    Integer variant = sc.nextInt();
                                    if (variant > 0) {
                                        println("answer.success", variant);
                                        incCountAnswer();
                                    }
                                }
                                String answer = sc.nextLine();
                                if (answer != null && answer.length() > 0) {
                                    println("answer.success", answer);
                                    incCountAnswer();
                                }
                            }
                    );
        println("resume", fio, countAnswer, questionList.size());
        sc.close();
    }

    /**
     * Увеличивает счетчик валидных ответов на 1.
     */
    private void incCountAnswer() {
        countAnswer ++;
    }

    /**
     * Выводит на консоль сообщение в заданной локали.
     *
     * @param message сообщение
     */
    private void println(String message) {
        System.out.println(
            messageSource.getMessage(message, null, LocaleUtils.toLocale(locale))
        );
    }
    /**
     * Выводит на консоль параметризированное сообщение в заданной локали.
     *
     * @param message параметризированное сообщение
     * @param param параметры сообщения
     */
    private void println(String message, Object... param) {
        System.out.println(
                messageSource.getMessage(message, param, LocaleUtils.toLocale(locale))
        );
    }
}
