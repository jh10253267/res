package com.studioreservation.domain.fcm.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService {
    private final FirebaseApp firebaseApp;

    public void subscribeToTopic(String token, String topic) throws FirebaseMessagingException {
        FirebaseMessaging.getInstance(firebaseApp)
                .subscribeToTopic(Collections.singletonList(token), topic);
    }

    /**
     * 데이터 메시지 토픽 전송
     */
    public String sendDataToTopic(String topic, Map<String, String> data) throws FirebaseMessagingException {
        Message.Builder builder = Message.builder().setTopic(topic);

        data.forEach(builder::putData);

        Message message = builder.build();
        return FirebaseMessaging.getInstance(firebaseApp).send(message);
    }
}
