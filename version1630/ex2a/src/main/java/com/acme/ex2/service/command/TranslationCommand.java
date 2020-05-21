package com.acme.ex2.service.command;

import com.acme.common.business.exception.CommandException;
import com.acme.common.service.AbstractCommand;
import com.acme.common.service.AbstractCommand.Usecase;
import com.acme.ex2.business.impl.EnglishToFrenchTranslationCommandHandler;
import com.acme.ex2.business.impl.EnglishToSpanishTranslationCommandHandler;
import com.acme.ex2.business.impl.FrenchToSpanishTranslationCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Usecase(handlers={
		EnglishToFrenchTranslationCommandHandler.class,
		FrenchToSpanishTranslationCommandHandler.class,
		EnglishToSpanishTranslationCommandHandler.class,		
}, parallelHandling=true)
public class TranslationCommand extends AbstractCommand {

	private String textIn;
	private String langIn;
	private String textOut;
	private String langOut;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public String getTextIn() {
		return textIn;
	}

	public void setTextIn(String textIn) {
		this.textIn = textIn;
	}

	public String getLangIn() {
		return langIn;
	}

	public void setLangIn(String langIn) {
		this.langIn = langIn;
	}

	public String getTextOut() {
		return textOut;
	}

	public void setTextOut(String textOut) {
		this.textOut = textOut;
	}

	public String getLangOut() {
		return langOut;
	}

	public void setLangOut(String langOut) {
		this.langOut = langOut;
	}

	@Override
	public void validateStateBeforeHandling() throws CommandException {
		super.validateStateBeforeHandling();
		if(this.langIn==null){
			throw new CommandException("langIn cannot be null");
		}
		if(this.langOut==null){
			throw new CommandException("langOut cannot be null");
		}
		if(this.textIn==null){
			throw new CommandException("textIn cannot be null");
		}

	}

	@Override
	public void validateStateAfterHandling() throws CommandException {
		super.validateStateAfterHandling();
		if(this.textOut == null){
			logger.warn("output.value could not be computed");
		}
	}
}
