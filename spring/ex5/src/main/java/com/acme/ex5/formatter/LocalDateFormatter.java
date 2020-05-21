package com.acme.ex5.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

public class LocalDateFormatter implements Formatter<LocalDate> {
	private final DateTimeFormatter formatter;

	public LocalDateFormatter(String pattern) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}

	@Override
	public String print(LocalDate object, Locale locale) {
		return object != null ? object.format(formatter) : null;
	}

	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException {
		return StringUtils.hasText(text) ? LocalDate.parse(text, formatter) : null;
	}
}
