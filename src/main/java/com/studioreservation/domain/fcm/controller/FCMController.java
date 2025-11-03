package com.studioreservation.domain.fcm.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.studioreservation.domain.fcm.service.FCMService;
import com.studioreservation.global.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fcm-test")
public class FCMController {
    private final FCMService service;
    @GetMapping("/subscribe")
    public APIResponse<?> subscribe(@RequestParam String token) {
        try {
            service.subscribeToTopic(token, "admin");
            return APIResponse.success("✅ 토픽 'admin' 구독 성공!");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return APIResponse.error("❌ 토픽 구독 실패: " + e.getMessage());
        }
    }

    /**
     * 테스트용: 토픽 메시지 전송
     * query param ?message=Hello
     */
    @GetMapping("/send")
    public APIResponse<?> send(@RequestParam String message) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("type", "TEST");
            data.put("message", message);

            String response = service.sendDataToTopic("reservation", data);
            return APIResponse.success("✅ 메시지 전송 완료: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return APIResponse.error("❌ 메시지 전송 실패: " + e.getMessage());
        }
    }
}
