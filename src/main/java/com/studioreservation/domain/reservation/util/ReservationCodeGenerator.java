package com.studioreservation.domain.reservation.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ReservationCodeGenerator {
    @Value("${base62.charset}")
    private String BASE62;
    private static final int MAX_RETRY = 5;

    private String encodeBase62(long baseSn) {
        StringBuilder sb = new StringBuilder();
        while (baseSn > 0) {
            sb.append(BASE62.charAt((int)(baseSn % 62)));
            baseSn /= 62;
        }
        return sb.reverse().toString();
    }

    public String generateReservationCode(long id) {
        long OFFSET = 1_000_000L;
        String code = encodeBase62(OFFSET + id);
        return padWithRandomChars(code, 6, BASE62);
    }

    private static String padWithRandomChars(String base62Code, int totalLength, String base62Charset) {
        int padLength = totalLength - base62Code.length();
        if (padLength <= 0) return base62Code;

        SecureRandom random = new SecureRandom();
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < padLength; i++) {
            int index = random.nextInt(62);
            padding.append(base62Charset.charAt(index));
        }

        return padding + base62Code;
    }
}
