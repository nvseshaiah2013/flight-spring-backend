package com.cg.flight.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;




@Entity
@Table(name="flight_master")
public class Flight implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length=5)
	private String flight_code;
	@Column(length=20)
	private String flight_name;
	@Column(length=20)
	private String source;
	@Column(length=20)
	private String destination;
	@Column(name="startDate")	
	private Timestamp date;
	private int price;
	private Integer vacant_seats;
	@OneToMany(mappedBy="flight",cascade=CascadeType.MERGE)
	private Set<Ticket> tickets = new HashSet<>();
	
	public Flight() {
		
	}
	
	public Flight(String flight_name, String source, String destination,Timestamp date, int price, String flight_code) {
		this.flight_name = flight_name;
		this.source = source;
		this.destination = destination;
		this.date = date;
		this.price = price;
		this.flight_code = flight_code;
		this.vacant_seats = 50;
	}
	
	public String getFlight_code() {
		return flight_code;
	}
	public void setFlight_code(String flight_code) {
		this.flight_code = flight_code;
	}
	public String getFlight_name() {
		return flight_name;
	}
	public void setFlight_name(String flight_name) {
		this.flight_name = flight_name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Integer getVacant_seats() {
		return vacant_seats;
	}
	public void setVacant_seats(Integer vacant_seats) {
		this.vacant_seats = vacant_seats;
	}
	public Set<Ticket> getTickets() {
		return tickets;
	}
	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	@Override
	public String toString()
	{
		String answer = " -------Flight Name : " + this.getFlight_name() + "--------- \n"
			 + " -------Source : " + this.getSource() + "\n"
			 + " -------Destination : " + this.getDestination() + "\n"
			 + " -------Price : " + this.getPrice();
		answer = answer + "-------Date : " + this.getDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "-----\n";
		answer = answer + "-------Time : " + this.getDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("hh:MM")) + "------\n";
		answer = answer + "-------Flight Code : " + this.getFlight_code() + "\n";
		answer = answer + "------- No. of Vacant Seats-------- : " + this. getVacant_seats();
		return answer;
	}
	public void addTicket(Ticket ticket)
	{
		ticket.setFlight(this);
		this.getTickets().add(ticket);
	}
	
}
