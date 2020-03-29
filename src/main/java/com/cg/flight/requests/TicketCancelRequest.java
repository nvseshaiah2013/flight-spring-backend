package com.cg.flight.requests;

public class TicketCancelRequest {
	
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
	public TicketCancelRequest() {}

}
