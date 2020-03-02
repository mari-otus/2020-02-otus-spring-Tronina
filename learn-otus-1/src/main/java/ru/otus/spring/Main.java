package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionaryServiceImpl;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionService questionService = context.getBean(QuestionService.class);
        QuestionaryServiceImpl questionaryService = context.getBean(QuestionaryServiceImpl.class);

        questionaryService.runQuestionary(questionService.getQuestionList());

    }
}
