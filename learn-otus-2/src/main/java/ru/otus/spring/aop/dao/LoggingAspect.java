package ru.otus.spring.aop.dao;

import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.QuestionDaoSimple;

import java.util.List;

/**
 * Логгирование методов DAO {@link QuestionDaoSimple}.
 *
 * @author Mariya Tronina
 * @since 11.03.2020
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Логгирование метода findAllQuestion.
     *
     * @param joinPoint точка входа
     * @param result    результат выполнения метода
     */
    @AfterReturning(
            pointcut = "execution(* ru.otus.spring.dao.QuestionDaoSimple.findAllQuestion(..))",
            returning = "result"
    )
    public void logBefore(JoinPoint joinPoint, List result) {
        System.out.println("Вопросы получены. Всего вопросов: " + CollectionUtils.size(result));
        System.out.println("Начинаем тестирование");
        System.out.println("---------------------------------------");
    }
}
