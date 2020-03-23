package com.cg.flight.dao;

import java.util.List;

import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;

public class TicketDao implements ITicketDao {

	@Override
	public List<Ticket> getTickets(User user) {
		
		return null;
	}

	@Override
	public boolean cancelTicket(Ticket ticket) {
		
		return false;
	}

}
