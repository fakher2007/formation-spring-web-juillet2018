package com.acme.common.service.impl;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.common.business.CommandException;
import com.acme.ex3.ApplicationConfig;
import com.acme.ex3.business.filter.BookFilter;
import com.acme.ex3.model.entity.Book;
import com.acme.ex3.service.command.FindBookCommand;
import com.acme.ex3.service.command.MemberRegistrationCommand;
import com.acme.ex3.service.command.ReservationCommand;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
@ContextConfiguration(classes=ApplicationConfig.class)
public class CommandProcessorImplTest {

	@Autowired
    private CommandProcessorImpl processor;

    @Test
    public void testFindBookCommand() throws Exception{
        BookFilter filter = new BookFilter();
        filter.setTitle("%%");
        FindBookCommand cmd = new FindBookCommand();
        cmd.setFilter(filter);
        cmd = this.processor.process(cmd);
        for (Book book : cmd.getMultipleResults().getItems()) {
            System.out.println(String.format("%-40s %-40s %-40s", book.getTitle(), book.getAuthor().getFullname(), book.getCategory().getName()));
        }
    }
    
    @Test(expected=CommandException.class)
    public void testMemberRegistrationCommandOnError() throws Exception{
        MemberRegistrationCommand cmd = new MemberRegistrationCommand();
        cmd.setFirstname("Jane");
        cmd.setLastname("Doe");
        cmd.setUsername("jdoe");
        cmd.setPassword("azerty");
        this.processor.process(cmd);
    }
    
    //@Test
    public void testMemberRegistrationCommand() throws Exception{
        MemberRegistrationCommand cmd = new MemberRegistrationCommand();
        cmd.setFirstname("Mark");
        cmd.setLastname("Smith");
        cmd.setUsername("msmith");
        cmd.setPassword("azerty");
        cmd= this.processor.process(cmd);
        assertNotNull(cmd.getMember());
    }

    //@Test
    public void testReservationCommand() throws Exception{
        ReservationCommand cmd = new ReservationCommand();
        cmd.setBookId(1);
        cmd.setUsername("jdoe");
        cmd.setPickupDate(LocalDate.now().plusDays(1));
        cmd.setReturnDate(LocalDate.now().plusDays(10));
        cmd = this.processor.process(cmd);
        assertNotNull(cmd.getReservation());
        System.out.println(cmd.getReservation());
    }
    
    @Test
    public void testFindBookCommandAsync() throws Exception{
        BookFilter filter = new BookFilter();
        filter.setTitle("%%");
        FindBookCommand cmd = new FindBookCommand();
        cmd.setFilter(filter);
        System.out.println("before call to processAsync");
        
        this.processor.processAsync(cmd).thenAccept(c->{
	        for (Book book : cmd.getMultipleResults().getItems()) {
            	System.out.println(String.format("%-40s %-40s %-40s", book.getTitle(), book.getAuthor().getFullname(), book.getCategory().getName()));
	        }
        });
        System.out.println("after call to processAsync");
        Thread.sleep(2500);
    }
}