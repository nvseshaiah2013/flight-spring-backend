package com.cg.flight.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="FLIGHT_MASTER")
public class Flight implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="FLIGHT_CODE",length=5)
	@NotEmpty(message="Flight Code cannot be Empty")
	private String flight_code;
	
	@Column(name="FLIGHT_NAME",length=20)
	@NotNull(message="Flight Name cannot be null")
	@NotEmpty(message="Flight Name cannot be empty")
	private String flight_name;
	
	@Column(name="SOURCE",length=20)
	@NotNull(message="Source Cannot be Null")
	@NotEmpty(message="Source Cannot be Empty")
	private String source;
	
	@Column(name="DESTINATION",length=20)
	@NotNull(message="Destination Cannot be Null")
	@NotEmpty(message="Destination Cannot be Empty")
	private String destination;
	
	@Column(name="START_DATE")
	@NotNull(message="Date Cannot be Null")
	private Timestamp date;
	
	@Column(name="PRICE")
	@NotNull(message="Price cannot be null")
	@Min(value = 1,message="Price cannot be less than 1")
	private int price;
	
	@Column(name="VACANT_SEATS")
	@NotNull(message="Seats cannot be null")
	@Min(value=0,message="Seats cannot be negative")
	private int vacant_seats;
	
	@Column(name="CHECK_IN_BAGGAGE")
	@NotNull(message="Check In Baggage cannot be null")
	@Min(value=0,message="Check In Baggage cannot be negative")
	private int checkinbaggage;
	
	@Column(name="CARGO_BAGGAGE")
	@NotNull(message="Cargo Baggage cannot be null")
	@Min(value=0,message="Cargo Baggage cannot be negative")
	private int cargobaggage;
	
	@Column(name="IMAGE_PATH",length=20)
	@NotNull(message="Image path cannot be null")
	@NotEmpty(message="image path cannot be empty")
	private String image;
	
	@OneToMany(mappedBy="flight")
	private Set<Ticket> tickets = new HashSet<>();
	
	public Flight() {
		
	}
	
	
	public Flight(@NotEmpty(message = "Flight Code cannot be Empty") String flight_code,
			@NotNull(message = "Flight Name cannot be null") @NotEmpty(message = "Flight Name cannot be empty") @Min(value = 0, message = "Flight Name cannot be negative") String flight_name,
			@NotNull(message = "Source Cannot be Null") @NotEmpty(message = "Source Cannot be Empty") String source,
			@NotNull(message = "Destination Cannot be Null") @NotEmpty(message = "Destination Cannot be Empty") String destination,
			@NotNull(message = "Date Cannot be Null") Timestamp date,
			@NotNull(message = "Price cannot be null") @Min(value = 0, message = "Price cannot be less than 0") int price,
			@NotNull(message = "Seats cannot be null") @Min(value = 0, message = "Seats cannot be negative") int vacant_seats,
			@NotNull(message = "Check In Baggage cannot be null") @Min(value = 0, message = "Check In Baggage cannot be negative") int checkinbaggage,
			@NotNull(message = "Cargo Baggage cannot be null") @Min(value = 0, message = "Cargo Baggage cannot be negative") int cargobaggage,
			@NotNull(message="Image path cannot be null") @NotEmpty(message="image path cannot be empty") String image) {
		
		this.flight_code = flight_code;
		this.flight_name = flight_name;
		this.source = source;
		this.destination = destination;
		this.date = date;
		this.price = price;
		this.vacant_seats = vacant_seats;
		this.checkinbaggage = checkinbaggage;
		this.cargobaggage = cargobaggage;
		this.image = image;
	}
	
	public String getImage() {
		return image;
	}	
	
	public void setImage(String image) {
		this.image = image;
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


	public int getVacant_seats() {
		return vacant_seats;
	}


	public void setVacant_seats(int vacant_seats) {
		this.vacant_seats = vacant_seats;
	}


	public int getCheckinbaggage() {
		return checkinbaggage;
	}


	public void setCheckinbaggage(int checkinbaggage) {
		this.checkinbaggage = checkinbaggage;
	}


	public int getCargobaggage() {
		return cargobaggage;
	}


	public void setCargobaggage(int cargobaggage) {
		this.cargobaggage = cargobaggage;
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
		this.tickets.add(ticket);
	}
	
}
