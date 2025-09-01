package com.studioreservation.domain.reservation.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class Base32CodeGenerator {
    private static final char[] BASE32 = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();
    private static final int BASE = BASE32.length;

    private static final Random random = new Random();

    public static String generateCodeWithDate(LocalDateTime localDatetime) {
        String dateStr = localDatetime.format(DateTimeFormatter.ofPattern("yyMMdd"));
        long dateNumber = Long.parseLong(dateStr);

        String datePart = base32Encode(dateNumber);

        if (datePart.length() < 2) {
            datePart = padLeft(datePart, 2);
        } else if (datePart.length() > 3) {
            datePart = datePart.substring(datePart.length() - 3);
        }

        int randomLength = 6 - datePart.length();
        StringBuilder randomPart = new StringBuilder();

        for (int i = 0; i < randomLength; i++) {
            randomPart.append(BASE32[random.nextInt(BASE)]);
        }

        return datePart + randomPart;
    }

    private static String base32Encode(long number) {
        StringBuilder result = new StringBuilder();
        while (number > 0) {
            result.insert(0, BASE32[(int)(number % BASE)]);
            number /= BASE;
        }
        return result.toString();
    }
    private static String padLeft(String input, int length) {
        return String.format("%" + length + "s", input).replace(' ', '0');
    }
}
