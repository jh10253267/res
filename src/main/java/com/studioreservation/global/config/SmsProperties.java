package com.studioreservation.global.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "sms")
@Configuration
@Data
@EnableConfigurationProperties(SmsProperties.class)
public class SmsProperties {
    private String sender;
    private String key;
    private String secret;
}

