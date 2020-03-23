package com.cg.flight.dao;

import java.util.List;

import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;

public interface ITicketDao {
	List<Ticket> getTickets(User user);
	boolean cancelTicket(Ticket ticket);
}
