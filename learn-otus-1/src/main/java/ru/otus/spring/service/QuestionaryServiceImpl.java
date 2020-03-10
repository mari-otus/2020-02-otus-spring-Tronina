package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Сервис для запуска тестирования.
 *
 * @author Mariya Tronina mariya.tronina@rtlabs.ru
 * @since 02.03.2020
 */
public class QuestionaryServiceImpl implements QuestionaryService {

    /**
     * Счетчик валидных ответов.
     */
    private int countAnswer = 0;

    @Override
    public void runQuestionary(List<Question> questionList) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Укажите Ваши фамилию и имя:");
        String fio = sc.nextLine();
        questionList.stream()
                    .forEach(
                            question -> {
                                System.out.println(question.getQuestion());
                                if (question.getVariant() > 0) {
                                    Arrays.asList(question.getAnswers().split(","))
                                          .forEach(System.out::println);
                                    while (!sc.hasNext("[0-"+question.getVariant()+"]+")) {
                                        System.out.println("Вы выбрали неверный вариант ответа. Попробуйте ещё раз: ");
                                        sc.next();
                                    }
                                    Integer variant = sc.nextInt();
                                    if (variant > 0) {
                                        System.out.println("Ваш ответ: " + variant);
                                        incCountAnswer();
                                    }
                                }
                                String answer = sc.nextLine();
                                if (answer != null && answer.length() > 0) {
                                    System.out.println("Ваш ответ: " + answer);
                                    incCountAnswer();
                                }
                            }
                    );
        System.out.println(String.format("%s, Вы успешно прошли тест. \n" +
                           "Вы ответили на %s вопросов из %s", fio, countAnswer, questionList.size()));
        sc.close();
    }

    /**
     * Увеличивает счетчик валидных ответов на 1.
     */
    private void incCountAnswer() {
        countAnswer ++;
    }
}
