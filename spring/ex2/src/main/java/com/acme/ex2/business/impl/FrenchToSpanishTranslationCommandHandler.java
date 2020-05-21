package com.acme.ex2.business.impl;

import com.acme.common.business.CommandHandler;
import com.acme.ex2.ApplicationProperties;
import com.acme.ex2.service.command.TranslationCommand;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class FrenchToSpanishTranslationCommandHandler implements CommandHandler<TranslationCommand> {

	@Autowired
    private ApplicationProperties applicationProperties;

	@Autowired
    private Logger logger;

    @Override
    @EventListener(condition="#command.langIn=='fr' && #command.langOut=='sp'")
    public void handle(TranslationCommand command) {
        logger.info("I will try to handle {}", command.toString());

        // (1) traitement sémantique, syntaxique (spécifique à une traduction fr-sp), etc.. sur sentence
        // 2) accès au référentiel des mots et récupération du résultat
        Path path = Paths.get(this.applicationProperties.getFolder(), "fr-sp.txt");
        try {
            ArrayList<String> wordsOut = new ArrayList<>();
            for (String word : command.getTextIn().split(" ")) {
                Optional<String> result = Files.lines(path)
                        .map(line -> line.split(";"))
                        .filter(columns -> columns[0].equals(word))
                        .map(columns -> columns[1])
                        .findAny();
                wordsOut.add(result.orElse("!!" + word + "!!"));
            }

            // (3) traitement sémantique, syntaxique (spécifique à une traduction en-fr), etc.. sur le résultat
            // 4) utilisation du résultat pour valoriser la propriété textOut
            command.setTextOut(String.join(" ", wordsOut));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
