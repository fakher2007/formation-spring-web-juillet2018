package com.acme.ex2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:conf.properties")
public class ApplicationProperties {

	@Value("${folder}")
	private String folder;

	public String getFolder() {
		return folder;
	}
}
