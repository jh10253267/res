package com.studioreservation.global.config;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SMSConfig {
    @Value("${sms.api.key}")
    private String apiKey;

    @Value("${sms.api.secret}")
    private String secretKey;

    @Bean
    public DefaultMessageService ExampleController() {
        return NurigoApp.INSTANCE.initialize(apiKey,
                secretKey,
                "https://api.coolsms.co.kr");
    }
}
