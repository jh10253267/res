package com.studioreservation.global.formatter;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class TimestampDeserializer extends JsonDeserializer<Timestamp> {
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

	@Override
	public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		String dateStr = jsonParser.getText();
		try {
			Date parsedDate = FORMAT.parse(dateStr);
			return new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			throw new RuntimeException("Invalid date format. Expected: yyyyMMddHHmmss");
		}
	}
}
