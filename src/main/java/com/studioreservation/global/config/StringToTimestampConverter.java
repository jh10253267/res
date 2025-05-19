package com.studioreservation.global.config;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

public class StringToTimestampConverter implements Converter<String, Timestamp> {
	@Override
	public Timestamp convert(String src) {
		if (src == null || src.isBlank())
			return null;
		if (src.length() == 8)
			return Timestamp.valueOf(
				LocalDate.parse(src, DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay());
		if (src.length() == 14)
			return Timestamp.valueOf(
				LocalDateTime.parse(src, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
		throw new IllegalArgumentException("Expecting yyyyMMdd or yyyyMMddHHmmss");
	}
}
