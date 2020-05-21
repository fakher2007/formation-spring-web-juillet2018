package com.acme.ex3.business.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acme.common.business.CommandException;
import com.acme.common.business.CommandHandler;
import com.acme.common.persistence.ObjectStore;
import com.acme.common.service.AbstractCommand;
import com.acme.ex3.model.component.Account;
import com.acme.ex3.model.entity.Member;
import com.acme.ex3.service.command.MemberRegistrationCommand;

@Component
@Transactional(propagation=Propagation.MANDATORY)
public class MemberRegistrationCommandHandler implements CommandHandler {

	@Autowired
    private ObjectStore objectStore;

    @Override
    public void handle(AbstractCommand command, HandlingContext handlingContext) {
        if(!(command instanceof MemberRegistrationCommand)){
            return;
        }

        MemberRegistrationCommand cmd = (MemberRegistrationCommand)command;

        String query = "select m from Member m where m.account.username=:username";
        Optional<Member> _member = this.objectStore.findOne(Member.class, query, Map.of("username", cmd.getUsername()));
        if(_member.isPresent()){
            throw new CommandException("memberRegistration-account.already.exists", true);
        }

        Member member = new Member();
        member.setFirstname(cmd.getFirstname());
        member.setLastname(cmd.getLastname());

        Account account = new Account();
        account.setPassword(cmd.getPassword());
        account.setUsername(cmd.getUsername());
        member.setAccount(account);

        // TODO : sauvegarde du member
        this.objectStore.saveOrUpdate(member);

        cmd.setMember(member);
    }
}