package com.acme.ex2.service.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.common.service.CommandProcessor;
import com.acme.ex2.ApplicationConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class)
public class TranslationCommandTest {

	@Autowired
	private CommandProcessor processor;

	/*
	public CommandProcessorImplTest() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		this.processor = ctx.getBean(CommandProcessor.class);
	}*/
	
	@Test
	public void testProcess() throws Exception {
		TranslationCommand cmd = new TranslationCommand();
		cmd.setLangIn("en");
		cmd.setLangOut("fr");
		cmd.setTextIn("hello");
		this.processor.process(cmd);
		
		while(true) {
			this.processor.process(cmd);
			Assert.assertEquals("salut", cmd.getTextOut());
			Thread.sleep(2000);
		}
	}
}
