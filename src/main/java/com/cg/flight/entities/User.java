package com.cg.flight.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="user_master")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(length=25)
	private String name;
	@Id
	@Column(length=35)
	private String username;
	@Column(length=28)
	private String password;
	private int age;
	@OneToMany(mappedBy="user",cascade=CascadeType.MERGE)
	private Set<Ticket> tickets = new HashSet<>();	
	public User()
	{
		
	}
	
	public User(String name, String username, String password,int age) {	
		this.name = name;
		this.username = username;
		this.password = password;
		this.age = age;
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
	public static boolean validateUser(String name,String username,String password,int age) {
		if(name.isEmpty())
			return false;
		if(username.isEmpty())
			return false;
		if(!username.endsWith("@gmail.com"))
			return false;
		if(password.isEmpty())
			return false;
		if(age < 0)
			return false;	
		return true;
	}
	public void addTicket(Ticket ticket)
	{
		ticket.setUser(this);
		this.getTickets().add(ticket);
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	
}
