package ru.otus.spring.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.spring.config.properties.AppProperties;

/**
 * @author Mariya Tronina
 * @since 10.03.2020
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {

    @Bean
    public Resource resource(AppProperties appProperties) {
        return new ClassPathResource(appProperties.getFile());
    }

    @Bean
    public CsvMapper csvMapper() {
        return new CsvMapper();
    }

}
