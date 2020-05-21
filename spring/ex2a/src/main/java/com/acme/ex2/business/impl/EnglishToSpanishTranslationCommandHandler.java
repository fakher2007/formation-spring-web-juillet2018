package com.acme.ex2.business.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.acme.common.business.CommandHandler;
import com.acme.common.business.CommandHandler.Handler;
import com.acme.common.service.AbstractCommand;
import com.acme.ex2.ApplicationProperties;
import com.acme.ex2.service.command.TranslationCommand;

@Handler
public class EnglishToSpanishTranslationCommandHandler implements CommandHandler {

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private Logger logger;


	@Override
	public void handle(AbstractCommand command) {
		if (!(command instanceof TranslationCommand)) {
			logger.warn("I don't know how to handle a command of type {}", command.getClass().getName());
			return;
		}

		TranslationCommand cmd = (TranslationCommand) command;

		if (!("en".equals(cmd.getLangIn()) && "sp".equals(cmd.getLangOut()))) {
			logger.debug("do nothing, don't know how to handle a translation from {} to {}", cmd.getLangIn(), cmd.getLangOut());
			return;
		}
		logger.info("I can take care of {}", command.toString());
		// (1) traitement sémantique, syntaxique (spécifique à une traduction en-sp),
		// etc.. sur sentence
		// 2) accès au référentiel des mots et récupération du résultat
		Path path = Paths.get(this.applicationProperties.getFolder(), "en-sp.txt");
		try {
			ArrayList<String> wordsOut = new ArrayList<>();
			for (String word : cmd.getTextIn().split(" ")) {
				Optional<String> result = Files.lines(path)
						.map(line -> line.split(";"))
						.filter(columns -> columns[0].equals(word))
						.map(columns -> columns[1]).findAny();
				wordsOut.add(result.orElse("!!" + word + "!!"));
			}

			// (3) traitement sémantique, syntaxique (spécifique à une traduction en-sp),
			// etc.. sur le résultat
			// 4) utilisation du résultat pour valoriser la propriété output.value
			cmd.setTextOut(String.join(" ", wordsOut));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
