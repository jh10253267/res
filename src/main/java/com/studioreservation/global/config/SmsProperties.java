package com.studioreservation.global.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "sms")
@Component
@Data
public class SmsProperties {
    private String sender;
    private String key;
    private String secret;
}

