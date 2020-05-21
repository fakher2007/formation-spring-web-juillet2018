package com.acme.ex3.business.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.acme.common.business.CommandHandler;
import com.acme.common.persistence.ObjectStore;
import com.acme.common.persistence.Results;
import com.acme.common.service.AbstractCommand;
import com.acme.ex3.model.entity.Book;
import com.acme.ex3.service.command.FindBookCommand;

@Component
@Transactional(propagation=Propagation.MANDATORY)
public class FindBookCommandHandler implements CommandHandler {

	@Autowired
    private ObjectStore objectStore;

    @Override
    public void handle(AbstractCommand command, HandlingContext handlingContext) {
        if (!(command instanceof FindBookCommand)) {
            return;
        }
        FindBookCommand cmd = (FindBookCommand) command;
        if (cmd.getId() != null) {
            Optional<Book> _book = this.objectStore.getById(Book.class, cmd.getId(), b -> b.getComments());
            _book.ifPresent(b -> cmd.setSingleResult(b));
        }
        else{
            String title = cmd.getFilter().getTitle();
            String authorName = cmd.getFilter().getAuthorName();

            String query = "select b from Book b join b.author a ";
            query += "where (:title is null or b.title like :title) and (:author is null or a.lastname like :author)";
            Map<String, Object> args =new HashMap<>();
            args.put("author", authorName);
            args.put("title", title);

            Results<Book> results = this.objectStore.findMany(Book.class, query, args);

            cmd.setMultipleResults(results);
        }
        
        handlingContext.afterCommit.add(()->System.out.println("send mail"));
    }
}
