package com.studioreservation.global.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.studioreservation.global.formatter.TimestampToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            SimpleModule module = new SimpleModule();
            module.addSerializer(Timestamp.class, new TimestampToStringSerializer());
            builder.modules(module);
        };
    }
}
