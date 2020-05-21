package com.acme.ex3.business.impl;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acme.common.business.CommandException;
import com.acme.common.business.CommandHandler;
import com.acme.common.persistence.ObjectStore;
import com.acme.common.service.AbstractCommand;
import com.acme.ex3.model.entity.Book;
import com.acme.ex3.model.entity.Member;
import com.acme.ex3.model.entity.Reservation;
import com.acme.ex3.service.command.ReservationCommand;

@Component
@Transactional(propagation=Propagation.MANDATORY)
public class ReservationCommandHandler implements CommandHandler {

	@Autowired
    private ObjectStore objectStore;


    private boolean isBetween(LocalDate date, LocalDate d1, LocalDate d2){
        return (date.isAfter(d1) && date.isBefore(d2)) || date.equals(d1) || date.equals(d2);
    }

    @Override
    public void handle(AbstractCommand command, HandlingContext handlingContext) {
        if(!(command instanceof ReservationCommand)){
            return;
        }

        ReservationCommand cmd= (ReservationCommand)command;

        String query = "select m from Member m where m.account.username=:username";
        Map<String, Object> args = Map.of("username", cmd.getUsername());
        Member member= this.objectStore.findOne(Member.class, query, args)
        		.orElseThrow(()-> new CommandException("reservation-member.not.found", true));
        

        Predicate<Reservation> conflict = r-> isBetween(r.getPickupDate(), cmd.getPickupDate(), cmd.getReturnDate());

        if(member.getReservations().stream().anyMatch(conflict))
        {
            throw new CommandException("reservation-member.quota.exceeded", true);
        }

        Book book = this.objectStore.getById(Book.class, cmd.getBookId())
        		.orElseThrow(()-> new CommandException("reservation-book.not.found", true));

        if(book.getReservations().stream().anyMatch(conflict))
        {
            throw new CommandException("reservation-book.unavailable", true);
        }

        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setBook(book);
        reservation.setPickupDate(cmd.getPickupDate());
        reservation.setReturnDate(cmd.getReturnDate());
        // TODO : sauvegarde de la réservation
        this.objectStore.saveOrUpdate(reservation);
        // TODO : valorisation de la propriété reservation de la commande avec l'objet rerversation qui vient d'être sauvegardé.
        cmd.setReservation(reservation);
    }
}
