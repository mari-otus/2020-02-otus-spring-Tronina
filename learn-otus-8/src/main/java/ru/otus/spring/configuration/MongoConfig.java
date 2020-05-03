package ru.otus.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.repository.listener.CascadeDeletedListener;

/**
 * Конфигурация mongodb.
 *
 * @author Mariya Tronina
 */
@Configuration
public class MongoConfig {

    @Bean
    public CascadeDeletedListener beforeSaveListener() {
        return new CascadeDeletedListener();
    }
}
