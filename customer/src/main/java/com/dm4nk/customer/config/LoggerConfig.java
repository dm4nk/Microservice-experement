package com.dm4nk.customer.config;

import com.dm4nk.customer.logging.Level;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class LoggerConfig {

    @Value("${aop.loggers.enabled:FALSE}")
    private Boolean enabled;

    @Value("${aop.loggers.level:INFO}")
    private String defaultLoggingLevel;

    @Bean("loggingEnabled")
    public Boolean loggingEnabled() {
        return enabled;
    }

    @Bean("defaultLoggingLevel")
    public Level defaultLoggingLevel() {
        return Level.getEnumByValue(defaultLoggingLevel).orElse(Level.INFO);
    }
}
