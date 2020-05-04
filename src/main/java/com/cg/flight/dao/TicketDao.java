package com.cg.flight.dao;

import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;

@Repository
public class TicketDao implements ITicketDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Set<Ticket> getTickets(User user) {		
		Set<Ticket> tickets = user.getTickets();
		return tickets;
	}

	@Override
	public boolean cancelTicket(Ticket ticket) {
		ticket.setStatus("Cancelled");
		ticket.getFlight().setVacant_seats(ticket.getFlight().getVacant_seats()+1);
		Ticket status = entityManager.merge(ticket);
		return status!=null;
	}
	
	@Override
	public Ticket findById(int ticketId)
	{
		return entityManager.find(Ticket.class, ticketId);
	}

}
