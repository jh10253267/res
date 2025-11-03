package com.studioreservation.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.studioreservation.domain.fcm.property.FCMProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class FCMConfig {
    private final FCMProperties props;
    @Bean
    public FirebaseApp firebaseApp() throws Exception {
        String base64Key = props.getFirebaseServiceAccountKeyBase64();
        InputStream keyStream;

        if (base64Key != null && !base64Key.isEmpty()) {
            byte[] decoded = Base64.getDecoder().decode(base64Key);
            String keyJson = new String(decoded, StandardCharsets.UTF_8)
                    .replace("\\n", "\n"); // 반드시 \n을 실제 개행으로 변환
            keyStream = new ByteArrayInputStream(keyJson.getBytes(StandardCharsets.UTF_8));
        } else {
            keyStream = new FileInputStream("src/main/resources/firebase-service-account.json");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(keyStream))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
