package ru.otus.spring.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author Mariya Tronina
 * @since 10.03.2020
 */
@PropertySource("classpath:application.properties")
@Configuration
public class AppConfig {

    @Bean
    public Resource resource(@Value("${csv.file}") String csvName) {
        return new ClassPathResource(csvName);
    }

    @Bean
    public CsvMapper csvMapper() {
        return new CsvMapper();
    }

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/i18n/question");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
