package com.acme.ex5.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

public class StringFormatter implements Formatter<String> {

	@Override
	public String print(String object, Locale locale) {
		return object;
	}

	@Override
	public String parse(String text, Locale locale) throws ParseException {
		return StringUtils.hasText(text) ? text : null;
	}
}
