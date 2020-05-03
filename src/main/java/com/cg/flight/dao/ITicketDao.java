package com.cg.flight.dao;

import java.util.Set;

import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;

public interface ITicketDao {
	Set<Ticket> getTickets(User user);
	boolean cancelTicket(Ticket ticket);
	Ticket findById(int ticketId);
}
