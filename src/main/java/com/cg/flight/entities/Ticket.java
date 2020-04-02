package com.cg.flight.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ticket_master")
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ticket_id;
	
	@Column(length=10)
	@NotNull(message="Ticket Status Cannot be Null")
	@NotEmpty(message="Ticket Status Cannot be Empty")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="flight_code")
	private Flight flight;
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	
	@Column(length=30)
	@NotNull(message="Passenger Name cannot be null")
	@NotEmpty(message="Passenger Name cannot be empty")
	private String name;
	
	@NotNull(message="Passenger Age should not be null")
	@Min(value=5,message="Passenger Age must be greater than 5")
	private int age;
	
	@Column(length=10)
	@NotNull(message="Gender cannot be null")
	@NotEmpty(message="Gender cannot be empty")
	private String gender;
	
	@Column(length=15)
	@NotNull(message="Id Type cannot be null")
	@NotEmpty(message="Id type cannot be empty")
	private String idType;
	
	@Column(length=30)
	@NotNull(message="Id No. should not be null")
	@NotEmpty(message="Id No. should not be empty")
	private String idNo;
	
	
	public Ticket() {		
		
	}
	
	


	public Ticket(Flight flight, User user,
			@NotNull(message = "Passenger Name cannot be null") @NotEmpty(message = "Passenger Name cannot be empty") String name,
			@NotNull(message = "Passenger Age should not be null") @Min(value = 5, message = "Passenger Age must be greater than 5") int age,
			@NotNull(message = "Gender cannot be null") @NotEmpty(message = "Gender cannot be empty") String gender,
			@NotNull(message = "Id Type cannot be null") @NotEmpty(message = "Id type cannot be empty") String idType,
			@NotNull(message = "Id No. should not be null") @NotEmpty(message = "Id No. should not be empty") String idNo) {
		this.flight = flight;
		this.user = user;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.idType = idType;
		this.idNo = idNo;
		this.status = "Booked";
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public int getAge() {
		return age;
	}




	public void setAge(int age) {
		this.age = age;
	}




	public String getGender() {
		return gender;
	}




	public void setGender(String gender) {
		this.gender = gender;
	}




	public String getIdType() {
		return idType;
	}




	public void setIdType(String idType) {
		this.idType = idType;
	}




	public String getIdNo() {
		return idNo;
	}




	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}




	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	@JsonIgnore
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
