package ru.otus.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.otus.spring.service.QuestionaryService;
import ru.otus.spring.service.QuestionaryServiceImpl;

@EnableAspectJAutoProxy
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        QuestionaryService questionaryService = context.getBean(QuestionaryServiceImpl.class);

        questionaryService.runQuestionary();

    }
}
