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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TICKET_MASTER")
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name="TICKET_ID")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ticket_id;
	
	@Column(name="STATUS",length=10)
	@NotNull(message="Ticket Status Cannot be Null")
	@NotEmpty(message="Ticket Status Cannot be Empty")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="flight_code",nullable = false)
	private Flight flight;
	@ManyToOne
	@JoinColumn(name="username",nullable = false)
	private User user;
	
	@Column(name="PASSENGER_NAME",length=50)
	@NotNull(message="Passenger Name cannot be null")
	@NotEmpty(message="Passenger Name cannot be empty")
	@Pattern(regexp="([a-zA-Z]+[ ]?)+",message="Name can contain only alphabets, Single space separated.")
	private String name;
	
	@Column(name="PASSENGER_AGE")
	@NotNull(message="Passenger Age should not be null")
	@Min(value=5,message="Passenger Age must be greater than or equal to 5")
	@Max(value=122,message="Passenger Age must be less than or equal to 122")
	private int age;
	
	@Column(name="PASSENGER_GENDER",length=10)
	@NotNull(message="Gender cannot be null")
	@NotEmpty(message="Gender cannot be empty")
	@Pattern(regexp="Male|Female|Other",message="Gender can only be Male,Female,Other only.")
	private String gender;
	
	@Column(name="ID_TYPE",length=15)
	@NotNull(message="Id Type cannot be null")
	@NotEmpty(message="Id type cannot be empty")
	@Pattern(regexp="PAN|Aadhar|DL|Passport",message="Accepted Ids are Passport,Aadhar,DL,PAN only.")
	private String idType;
	
	@Column(name="ID_NO",length=30)
	@NotNull(message="Id No. should not be null")
	@NotEmpty(message="Id No. should not be empty")
	private String idNo;
	
	
	public Ticket() {	
		
	}
	


	public Ticket(Flight flight, User user,
			@NotNull(message = "Passenger Name cannot be null") @NotEmpty(message = "Passenger Name cannot be empty") @Pattern(regexp = "([a-zA-Z]+[ ]?)+", message = "Name can contain only alphabets, Single space separated.") String name,
			@NotNull(message = "Passenger Age should not be null") @Min(value = 5, message = "Passenger Age must be greater than or equal to 5") @Max(value = 122, message = "Passenger Age must be less than or equal to 122") int age,
			@NotNull(message = "Gender cannot be null") @NotEmpty(message = "Gender cannot be empty") @Pattern(regexp = "Male|Female|Other", message = "Gender can only be Male,Female,Other only.") String gender,
			@NotNull(message = "Id Type cannot be null") @NotEmpty(message = "Id type cannot be empty") @Pattern(regexp = "PAN|Aadhar|DL|Passport", message = "Accepted Ids are Passport,Aadhar,DL,PAN only.") String idType,
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
