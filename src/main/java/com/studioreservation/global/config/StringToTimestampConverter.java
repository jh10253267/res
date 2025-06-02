package com.studioreservation.global.config;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.core.convert.converter.Converter;

public class StringToTimestampConverter implements Converter<String, Timestamp> {

	private static final DateTimeFormatter FORMAT_8 = DateTimeFormatter.BASIC_ISO_DATE;
	private static final DateTimeFormatter FORMAT_14 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final ZoneId ZONE_KST = ZoneId.of("Asia/Seoul");

	@Override
	public Timestamp convert(String src) {
		if (src == null || src.isBlank()) {
			return null;
		}

		try {
			if (src.length() == 8) {
				LocalDate date = LocalDate.parse(src, FORMAT_8);
				ZonedDateTime zdt = date.atStartOfDay(ZONE_KST);
				return Timestamp.from(zdt.toInstant());
			}

			if (src.length() == 14) {
				LocalDateTime dateTime = LocalDateTime.parse(src, FORMAT_14);
				ZonedDateTime zdt = dateTime.atZone(ZONE_KST);
				return Timestamp.from(zdt.toInstant());
			}
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("잘못된 날짜 형식입니다: " + src, e);
		}

		throw new IllegalArgumentException("지원하지 않는 날짜 포맷입니다 (길이: " + src.length() + "). yyyyMMdd 또는 yyyyMMddHHmmss 형식이어야 합니다.");
	}
}