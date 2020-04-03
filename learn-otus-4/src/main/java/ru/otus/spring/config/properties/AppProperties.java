package ru.otus.spring.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Mariya Tronina
 */
@Data
@Component
@ConfigurationProperties(prefix = "csv")
public class AppProperties {

    private String file;
}
