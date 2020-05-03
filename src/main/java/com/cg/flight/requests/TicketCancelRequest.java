package com.cg.flight.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TicketCancelRequest {
	

	@NotNull(message = "Ticket Id Cannot be Null")
	@Positive(message ="Ticket Id Cannot be Negative")
	private int ticket_id;

	public int getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}

	public TicketCancelRequest(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	public TicketCancelRequest() {
		
	}

}
