package com.cg.flight.services;

import java.util.Set;

import com.cg.flight.entities.Ticket;

public interface ITicketService {

	Ticket findTicketById(int ticketId) throws Exception;

	Set<Ticket> getTickets(String username) throws Exception;

	boolean cancelTicket(int ticketId, String username) throws Exception;

}
