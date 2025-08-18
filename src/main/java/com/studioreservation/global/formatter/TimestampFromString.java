package com.studioreservation.global.formatter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimestampFromString {

    private static final DateTimeFormatter FORMAT_14 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter FORMAT_8 = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final ZoneId SEOUL_ZONE = ZoneId.of("Asia/Seoul");

    public static Timestamp parse(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }

        try {
            if (text.length() == 14) {
                LocalDateTime ldt = LocalDateTime.parse(text, FORMAT_14);
                ZonedDateTime zdt = ldt.atZone(SEOUL_ZONE);
                return Timestamp.from(zdt.toInstant());
            } else if (text.length() == 8) {
                LocalDate ld = LocalDate.parse(text, FORMAT_8);
                ZonedDateTime zdt = ld.atStartOfDay(SEOUL_ZONE);
                return Timestamp.from(zdt.toInstant());
            } else {
                throw new IllegalArgumentException("지원하지 않는 형식입니다. 8자리 또는 14자리 문자열이어야 합니다.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다: " + text, e);
        }
    }
}