package com.cg.flight.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER_MASTER")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="NAME",length = 50)
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name cannot be empty")
	@Pattern(regexp = "([a-zA-Z]+[ ]?)+", message = "Name should only contain alphabets, single space separated only.")
	private String name;

	@Id
	@Column(name="USERNAME",length = 50)
	@NotNull(message = "Username cannot be null")
	@NotEmpty(message = "Username cannot be empty")
	@Email(message = "Username must be a valid e-mail Id")
	private String username;

	@JsonIgnore
	public Set<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<Passenger> passengers) {
		this.passengers = passengers;
	}

	@Column(name="PASSWORD",length = 60)
	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password cannot be empty")
	private String password;

	@Column(name="AGE")
	@NotNull
	@Min(value = 5, message = "Minimum Age of User must be 5")
	@Max(value = 122, message = "Maximum Age of User must be 122")
	private int age;

	@Column(name="GENDER",length = 6)
	@NotNull(message = "Gender Cannot be Omitted")
	@NotEmpty(message = "Gender Cannot be Left Empty")
	@Pattern(regexp = "Male|Female|Other", message = "Gender can be Male,Female,Other only")
	private String gender;

	@OneToMany(mappedBy = "user")
	private Set<Ticket> tickets = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private Set<Passenger> passengers = new HashSet<>();

	public User() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	@JsonIgnore
	public Set<Ticket> getTickets() {
		return this.tickets;
	}

	public void addTicket(Ticket ticket) {
		ticket.setUser(this);
		this.tickets.add(ticket);
	}

	public void addPassenger(Passenger passenger) {
		passenger.setUser(this);
		this.passengers.add(passenger);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [age=" + age + ", gender=" + gender + ", name=" + name + ", password=" + password + ", username="
				+ username + "]";
	}

	public User(
			@NotNull(message = "Name cannot be null") @NotEmpty(message = "Name cannot be empty") @Pattern(regexp = "([a-zA-Z]+[ ]?)+", message = "Name should only contain alphabets") String name,
			@NotNull(message = "Username cannot be null") @NotEmpty(message = "Username cannot be empty") @Email(message = "Username must be a valid e-mail Id") String username,
			@NotNull(message = "Password cannot be null") @NotEmpty(message = "Password cannot be empty") String password,
			@NotNull @Min(value = 5, message = "Minimum Age of User must be 5") @Max(value = 122, message = "Maximum Age of User must be 122") int age,
			@NotNull(message = "Gender Cannot be Omitted") @NotEmpty(message = "Gender Cannot be Left Empty") @Pattern(regexp = "Male|Female|Other", message = "Gender can be Male,Female,Other only") String gender) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.age = age;
		this.gender = gender;
	}
	

}
