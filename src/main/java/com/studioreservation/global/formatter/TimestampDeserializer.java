package com.studioreservation.global.formatter;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class TimestampDeserializer extends JsonDeserializer<Timestamp> {
	private static final SimpleDateFormat FORMAT_14 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat FORMAT_8 = new SimpleDateFormat("yyyyMMdd");

	@Override
	public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String text = p.getText().trim();

		try {
			if (text.length() == 14) {
				Date d = FORMAT_14.parse(text);
				return new Timestamp(d.getTime());
			} else if (text.length() == 8) {
				Date d = FORMAT_8.parse(text);
				return new Timestamp(d.getTime());
			}
		} catch (ParseException ignore) { /* fall through */ }

		throw ctxt.weirdStringException(text, Timestamp.class,
			"Expecting yyyyMMdd or yyyyMMddHHmmss");
	}
}

