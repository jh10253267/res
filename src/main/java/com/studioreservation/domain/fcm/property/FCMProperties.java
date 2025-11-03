package com.studioreservation.domain.fcm.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "google.firebase")
public class FCMProperties {
    private String firebaseServiceAccountKeyBase64;
}
