package com.studioreservation.global.formatter;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class TimestampDeserializer extends JsonDeserializer<Timestamp> {
	private static final DateTimeFormatter FORMAT_14 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final DateTimeFormatter FORMAT_8 = DateTimeFormatter.ofPattern("yyyyMMdd");

	@Override
	public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String text = p.getText().trim();

		try {
			ZoneId seoulZone = ZoneId.of("Asia/Seoul");

			if (text.length() == 14) {
				LocalDateTime ldt = LocalDateTime.parse(text, FORMAT_14);
				ZonedDateTime zdt = ldt.atZone(seoulZone);
				return Timestamp.from(zdt.toInstant());
			} else if (text.length() == 8) {
				LocalDate ld = LocalDate.parse(text, FORMAT_8);
				ZonedDateTime zdt = ld.atStartOfDay(seoulZone);
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