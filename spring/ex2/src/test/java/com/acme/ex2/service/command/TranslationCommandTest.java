package com.acme.ex2.service.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.common.service.impl.CommandProcessorImpl;
import com.acme.ex2.ApplicationConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {ApplicationConfig.class})
public class TranslationCommandTest {


	@Autowired
	private CommandProcessorImpl processor;

	@Test
	public void testProcess() {
		TranslationCommand cmd = new TranslationCommand();
		cmd.setTextIn("hello");
		cmd.setLangIn("en");
		cmd.setLangOut("fr");

		cmd = this.processor.process(cmd);
		Assert.assertEquals("salut", cmd.getTextOut());
	}
}
