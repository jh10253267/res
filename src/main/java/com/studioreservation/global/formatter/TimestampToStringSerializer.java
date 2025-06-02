package com.studioreservation.global.formatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampToStringSerializer extends JsonSerializer<Timestamp> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final ZoneId ZONE_KST = ZoneId.of("Asia/Seoul");

    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            ZonedDateTime zdt = value.toInstant().atZone(ZONE_KST);
            String formatted = zdt.format(FORMATTER);
            gen.writeString(formatted);
        } else {
            gen.writeNull();
        }
    }
}
