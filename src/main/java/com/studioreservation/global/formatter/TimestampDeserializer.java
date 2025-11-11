package com.studioreservation.global.formatter;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class TimestampDeserializer extends JsonDeserializer<Timestamp> {

    private static final DateTimeFormatter FORMAT_14 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter FORMAT_8 = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final ZoneId SEOUL_ZONE = ZoneId.of("Asia/Seoul");

    @Override
    public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText().trim();

        if (text == null || text.isBlank()) {
            return null; // 안전하게 null 처리
        }

        try {
            if (text.length() == 14) {
                LocalDateTime ldt = LocalDateTime.parse(text, FORMAT_14);

                // JVM default timezone이 KST라면 중복 적용 방지
                ZonedDateTime zdt = ldt.atZone(SEOUL_ZONE);

                return Timestamp.from(zdt.toInstant());

            } else if (text.length() == 8) {
                LocalDate ld = LocalDate.parse(text, FORMAT_8);
                ZonedDateTime zdt = ld.atStartOfDay(SEOUL_ZONE);
                return Timestamp.from(zdt.toInstant());
            }
        } catch (DateTimeParseException e) {
            throw ctxt.weirdStringException(text, Timestamp.class,
                    "Expecting yyyyMMdd or yyyyMMddHHmmss");
        }

        throw ctxt.weirdStringException(text, Timestamp.class,
                "Unrecognized timestamp format (must be length 8 or 14)");
    }
}
