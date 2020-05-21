package com.acme.ex3.service.command;

import com.acme.common.business.CommandException;
import com.acme.common.persistence.Results;
import com.acme.common.service.AbstractCommand;
import com.acme.common.service.AbstractCommand.Usecase;
import com.acme.ex3.business.filter.BookFilter;
import com.acme.ex3.business.impl.FindBookCommandHandler;
import com.acme.ex3.model.entity.Book;

@Usecase(handlers=FindBookCommandHandler.class)
public class FindBookCommand extends AbstractCommand {

    // inputs
    private Integer id;
    private BookFilter filter;
    // outputs
    private Book singleResult;
    private Results<Book> multipleResults;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookFilter getFilter() {
        return filter;
    }

    public void setFilter(BookFilter filter) {
        this.filter = filter;
    }

    public Book getSingleResult() {
        return singleResult;
    }

    public void setSingleResult(Book singleResult) {
        this.singleResult = singleResult;
    }

    public Results<Book> getMultipleResults() {
        return multipleResults;
    }

    public void setMultipleResults(Results<Book> multipleResults) {
        this.multipleResults = multipleResults;
    }


    @Override
    public void validateStateBeforeHandling() {
        super.validateStateBeforeHandling();
        if (this.filter == null && this.id == null) {
            throw new CommandException("id and filter can be null but not simultaneously", false);
        }
    }
}
