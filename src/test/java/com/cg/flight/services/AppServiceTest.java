package com.cg.flight.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.exceptions.InvalidPassengerException;
import com.cg.flight.exceptions.InvalidRequestException;
import com.cg.flight.exceptions.NotFound;
import com.cg.flight.exceptions.UserRegistrationException;
import com.cg.flight.requests.BookFlightRequest;
import com.cg.flight.requests.LoginRequest;
import com.cg.flight.responses.LoginResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AppServiceTest {

	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPassengerService passengerService;
	
	@Autowired
	private ITicketService ticketService;
	
	@Autowired 
	
	private IFlightService flightService;

	/**
	 * User Service Test Cases
	 * 
	 * */
	@Test
	@DisplayName("User Registration Successful")
	@Rollback(true)
	public void newUserRegistration() {
		User user = new User("Sample User","sample@gmail.com","Password@123",12,"Male");
		User user_from_db = new User();
		try {
			
			this.userService.registerUser(user);
			user_from_db = this.userService.findById("sample@gmail.com");
		}
		catch(Exception e) {
			
		}
		assertEquals("sample@gmail.com",user_from_db.getUsername());
	}
	
	
	@Test
	@DisplayName("Successfull User Login")
	@Rollback(true)
	public void successfulUserLogin() throws Exception {
		User user = new User("Sample User","sample@gmail.com","Password@123",12,"Male");
		LoginResponse login_response = new LoginResponse();
		try {
			
			this.userService.registerUser(user);			
			login_response = this.userService.getAuthenticationToken(new LoginRequest(user.getUsername(),"Password@123"));
		}
		catch(Exception e) {
//			
		}
		assertEquals("sample@gmail.com",login_response.getUser().getUsername());
	}
	
	@Test
	@DisplayName("User Login with wrong Password")
	@Rollback(true)
	public void unsuccessfulUserLogin() throws Exception  {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");		
			
			this.userService.registerUser(user);
			assertThrows(Exception.class,()->{
				
				this.userService.getAuthenticationToken(new LoginRequest(user.getUsername(),"RandomPassword"));
			});			
	}
	

	@Test
	@DisplayName("User Login Without Registration")
	public void loginWithoutRegTest() {
		LoginRequest request = new LoginRequest("sample@gmail.com","password");
		String message = "";
		try {
			this.userService.getAuthenticationToken(request);
		}
		catch(Exception e) {
			message = e.getMessage();
		}
		assertEquals("Incorrect username or password",message);
	}
	
	@Test
	@DisplayName("User Login Without Password")
	public void loginWithoutPassword() {
		LoginRequest request = new LoginRequest("venkata@gmail.com","");
		String message = "";
		try {
			this.userService.getAuthenticationToken(request);
		}
		catch(Exception e) {
			message =e.getMessage();
		}
		assertEquals("Incorrect username or password",message);
	}
	
	@Test
	@DisplayName("Checking Unique Constraint By Entering User with same Username")
	@Rollback(true)
	public void doubleUserRegistration() {
		User user1 = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		User user2 = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		assertThrows(UserRegistrationException.class,()->{
			this.userService.registerUser(user1);
			this.userService.registerUser(user2);
		});
	}

	/*
	 * Passenger Service Test Cases
	 * 
	 * **/
	
	@Test
	@DisplayName("Passenger Not Found is Thrown When invalid Id Is passed")
	public void passengerNotFound(){
		assertThrows(NotFound.class,()->{
			this.passengerService.findPassengerById(0);
		});
	}
	
	
	@Test
	@DisplayName("Passenger is successfully returned when Valid Id is Passed")
	@Rollback(true)
	public void passengerIsFound() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");		
		this.userService.registerUser(user);
		Passenger passenger = new Passenger("PAN","ABCDE1234A","Passenger A",22,"Male");
		this.passengerService.addPassenger(passenger, "sample1@gmail.com");
		
		Passenger searched_passenger = this.passengerService.findPassengerById(passenger.getPassenger_id());
		assertNotNull(searched_passenger);
	}
	
	@Test
	@DisplayName("Exception is thrown when passengers associated with non existent user is asked")
	public void passengersOfNonExistingUser() {		
		assertThrows(NotFound.class,()->{			
			this.passengerService.getPassengers("sample@gmail.com");
		});
	}
	
	@Test
	@DisplayName("Passengers Associated with the user are returned")
	@Rollback(true)
	public void getTheListOfPassengersOfGivenUser() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		Passenger passenger1 = new Passenger("PAN","ABCDE1234A","Passenger A",22,"Male");
		Passenger passenger2 = new Passenger("PAN","AQWER1234A","Passenger A",22,"Male");
		this.passengerService.addPassenger(passenger1, user.getUsername());
		this.passengerService.addPassenger(passenger2, user.getUsername());
		Set<Passenger> passengers = this.passengerService.getPassengers(user.getUsername());
		boolean isAdded = passengers.size() == 2;
		assertTrue(isAdded);
	}
	
	@Test
	@DisplayName("Passengers is Added to Database Successfully")
	@Rollback(true)
	public void passengerGetsAdded() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		Passenger passenger = new Passenger("PAN","ABCDE1234A","Passenger A",22,"Male");
		this.passengerService.addPassenger(passenger, "sample1@gmail.com");
		Passenger passenger_from_db = this.passengerService.findPassengerById(passenger.getPassenger_id());
		assertNotNull(passenger_from_db);
	}
	
	@Test
	@DisplayName("Passengers is Do not get Added When added to a non existent user")
	@Rollback(true)
	public void passengerdoNotGetsAdded() {
		Passenger passenger = new Passenger("PAN","ABCDE1234A","Passenger A",22,"Male");
		assertThrows(NotFound.class,()->{
			this.passengerService.addPassenger(passenger, "sample1@gmail.com");
		});
	}
	
	@Test
	@DisplayName("Passenger with wrong PAN Credentials Do Not Get Added")
	@Rollback(true)
	public void wrongPassengerPANDonotGetAdded()  throws Exception {
		Passenger passenger = new Passenger("PAN","AeeeE1234A","Passenger A",22,"Male");
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		
		assertThrows(InvalidPassengerException.class,()->{			
		this.passengerService.addPassenger(passenger, user.getUsername());});
	}
	
	@Test
	@DisplayName("Passenger with wrong Passport Credentials Do Not Get Added")
	@Rollback(true)
	public void wrongPassengerPassportDonotGetAdded()  throws Exception {
		Passenger passenger = new Passenger("Passport","AeeeE1234A","Passenger A",22,"Male");
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		
		assertThrows(InvalidPassengerException.class,()->{			
		this.passengerService.addPassenger(passenger, user.getUsername());});
	}
	
	@Test
	@DisplayName("Passenger with wrong Aadhar Credentials Do Not Get Added")
	@Rollback(true)
	public void wrongPassengerAadhartDonotGetAdded()  throws Exception {
		Passenger passenger = new Passenger("Aadhar","AeeeE1234A","Passenger A",22,"Male");
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		
		assertThrows(InvalidPassengerException.class,()->{			
		this.passengerService.addPassenger(passenger, user.getUsername());});
	}
	
	@Test
	@DisplayName("Passenger with wrong Driving License Credentials Do Not Get Added")
	@Rollback(true)
	public void wrongPassengerDLDonotGetAdded()  throws Exception {
		Passenger passenger = new Passenger("DL","AeeeE1234A","Passenger A",22,"Male");
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		
		assertThrows(InvalidPassengerException.class,()->{			
		this.passengerService.addPassenger(passenger, user.getUsername());});
	}
	
	@Test
	@DisplayName("Passenger with wrong PAN Credentials Do Not Get Updated")
	@Rollback(true)
	public void wrongPassengerPANUpdateDoNotGetUpdated() throws Exception {
		Passenger passenger = new Passenger("PAN","ABCDE1234A","Passenger A",22,"Male");
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		assertThrows(InvalidPassengerException.class,()->{			
			this.passengerService.addPassenger(passenger, "sample1@gmail.com");
			Passenger passenger_from_db = this.passengerService.findPassengerById(passenger.getPassenger_id());
			passenger_from_db.setIdNo("Ajkdfssdf");
			this.passengerService.updatePassenger(passenger_from_db, "sample1@gmail.com", passenger.getPassenger_id());
		});
	}
	
	@Test
	@DisplayName("Passenger with wrong Passport Credentials Do Not Get Updated")
	@Rollback(true)
	public void wrongPassengerPassportUpdateDoNotGetUpdated() throws Exception {
		Passenger passenger = new Passenger("Passport","A1234567","Passenger A",22,"Male");
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		assertThrows(InvalidPassengerException.class,()->{			
			this.passengerService.addPassenger(passenger, "sample1@gmail.com");
			Passenger passenger_from_db = this.passengerService.findPassengerById(passenger.getPassenger_id());
			passenger_from_db.setIdNo("Ajkdfssdf");
			this.passengerService.updatePassenger(passenger_from_db, "sample1@gmail.com", passenger.getPassenger_id());
		});
	}
	
	@Test
	@DisplayName("Passenger with wrong Aadhar Credentials Do Not Get Updated")
	@Rollback(true)
	public void wrongPassengerAadharUpdateDoNotGetUpdated() throws Exception {
		Passenger passenger = new Passenger("Aadhar","234567890123","Passenger A",22,"Male");
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		assertThrows(InvalidPassengerException.class,()->{			
			this.passengerService.addPassenger(passenger, "sample1@gmail.com");
			Passenger passenger_from_db = this.passengerService.findPassengerById(passenger.getPassenger_id());
			passenger_from_db.setIdNo("Ajkdfssdf");
			this.passengerService.updatePassenger(passenger_from_db, "sample1@gmail.com", passenger.getPassenger_id());
		});
	}
	
	@Test
	@DisplayName("Passenger with wrong Driving License Credentials Do Not Get Updated")
	@Rollback(true)
	public void wrongPassengerDLUpdateDoNotGetUpdated() throws Exception {
		Passenger passenger = new Passenger("DL","AP0220191234567","Passenger A",22,"Male");
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		assertThrows(InvalidPassengerException.class,()->{			
			this.passengerService.addPassenger(passenger, "sample1@gmail.com");
			Passenger passenger_from_db = this.passengerService.findPassengerById(passenger.getPassenger_id());
			passenger_from_db.setIdNo("Ajkdfssdf");
			this.passengerService.updatePassenger(passenger_from_db, "sample1@gmail.com", passenger.getPassenger_id());
		});
	}
	
	@Test
	@DisplayName("Successfully Deletes Existing passenger")
	@Rollback(true)
	public void deleteExistingPassenger() throws Exception {
		Passenger passenger = new Passenger("DL","MH0220201234567","Passenger A",22,"Male");
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		this.passengerService.addPassenger(passenger, user.getUsername());
		this.passengerService.deletePassenger(passenger.getPassenger_id(), user.getUsername());
		assertThrows(NotFound.class,()->{
			this.passengerService.findPassengerById(passenger.getPassenger_id());
		});
	}	
	
	@Test
	@DisplayName("Cannot Delete Passenger owned by other User")
	@Rollback(true)
	public void cannotDeletePassengerOwnedByOther() throws Exception {
		Passenger passenger = new Passenger("DL","MH0220191234567","Passenger A",22,"Male");
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		this.passengerService.addPassenger(passenger, user.getUsername());
		assertThrows(NotFound.class,()->{
				this.passengerService.deletePassenger(passenger.getPassenger_id(), "hello@gmail.com");
		});
	}
	
	
	/**
	 * Ticket Service
	 * 
	 * */
	
	@Test
	@DisplayName("Get Tickets for an Existing User")
	@Rollback(true)
	public void getTicketsOfExistingUser()  throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		Set<Ticket> tickets = this.ticketService.getTickets(user.getUsername());
		assertNotNull(tickets);
	}
	
	@Test
	@DisplayName("Getting Tickets for a non-Existing User returns Exception")
	@Rollback(true)
	public void getTicketsOfNonExistingUser() {
		
		assertThrows(NotFound.class,()->{
		 this.ticketService.getTickets("sample@gmail.com");
			
		});
	}

	@Test
	@DisplayName("Cancelling a non existing ticket")
	@Rollback(true)
	public void cancelNonExistingTicket() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		assertThrows(NotFound.class,()->{
			this.ticketService.cancelTicket(1, "sample@gmail.com");
			
		});
	}
	
	@Test
	@DisplayName("Cancelling a Already Booked Ticket")
	@Rollback(true)
	public void cancelAlreadyBookedTicket() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		BookFlightRequest request = new BookFlightRequest("VI011","Passenger",21,"Male","PAN","ABCDE1234D");
		request.setUsername(user.getUsername());
		Ticket ticket = this.flightService.bookFlight(request);
		this.ticketService.cancelTicket(ticket.getTicket_id(), user.getUsername());
		
		assertThrows(InvalidRequestException.class,()->{
			this.ticketService.cancelTicket(ticket.getTicket_id(), user.getUsername());
		});
		
	}
	
	@Test
	@DisplayName("Cancelling a Booked Ticket")
	@Rollback(true)
	public void cancelBookedTicket() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		BookFlightRequest request = new BookFlightRequest("VI011","Passenger",21,"Male","PAN","ABCDE1234D");
		request.setUsername(user.getUsername());
		Ticket ticket = this.flightService.bookFlight(request);
		this.ticketService.cancelTicket(ticket.getTicket_id(), user.getUsername());
		Ticket cancelledTicket = this.ticketService.findTicketById(ticket.getTicket_id());
		assertEquals(cancelledTicket.getStatus(),"Cancelled");
		
	}
	
	 /* Flight Service Tests
	*
	*
	*/
	
	@Test
	@DisplayName("Booking Ticket With Wrong PAN Credentials")
	@Rollback(true)
	public void bookTicketWithWrongPAN() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		BookFlightRequest request = new BookFlightRequest("VI011","Passenger",21,"Male","PAN","Ayyyy1234D");
		request.setUsername(user.getUsername());
		assertThrows(InvalidPassengerException.class,
				()-> {this.flightService.bookFlight(request);
				});
		
	}
	
	@Test
	@DisplayName("Booking Ticket With Wrong Aadhar Credentials")
	@Rollback(true)
	public void bookTicketWithWrongAadhar() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		BookFlightRequest request = new BookFlightRequest("VI011","Passenger",21,"Male","Aadhar","Ayyyy1234D");
		request.setUsername(user.getUsername());
		assertThrows(InvalidPassengerException.class,
				()-> {this.flightService.bookFlight(request);
				});
		
	}
	
	@Test
	@DisplayName("Booking Ticket With Wrong Driving License Credentials")
	@Rollback(true)
	public void bookTicketWithWrongDrivingLicense() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		BookFlightRequest request = new BookFlightRequest("VI011","Passenger",21,"Male","DL","Ayyyy1234D");
		request.setUsername(user.getUsername());
		assertThrows(InvalidPassengerException.class,
				()-> {this.flightService.bookFlight(request);
				});
		
	}
	
	@Test
	@DisplayName("Booking Ticket With Wrong Passport Credentials")
	@Rollback(true)
	public void bookTicketWithWrongPassport() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		BookFlightRequest request = new BookFlightRequest("VI011","Passenger",21,"Male","Passport","Ayyyy1234D");
		request.setUsername(user.getUsername());
		assertThrows(InvalidPassengerException.class,
				()-> {this.flightService.bookFlight(request);
				});
		
	}
	
	
	@Test
	@DisplayName("Booking Ticket With Right PAN Credentials")
	@Rollback(true)
	public void bookTicketWithRightPAN() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		BookFlightRequest request = new BookFlightRequest("VI011","Passenger",21,"Male","PAN","AQWRE1234D");
		request.setUsername(user.getUsername());
		Ticket ticket = this.flightService.bookFlight(request);
		assertNotNull(ticket);	
		
	}
	
	@Test
	@DisplayName("Booking Ticket With Right Aadhar Credentials")
	@Rollback(true)
	public void bookTicketWithRightAadhar() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		BookFlightRequest request = new BookFlightRequest("VI011","Passenger",21,"Male","Aadhar","200000000001");
		request.setUsername(user.getUsername());
		Ticket ticket = this.flightService.bookFlight(request);
		assertNotNull(ticket);	
		
	}
	
	@Test
	@DisplayName("Booking Ticket With Right Driving License Credentials")
	@Rollback(true)
	public void bookTicketWithRightDrivingLicense() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		BookFlightRequest request = new BookFlightRequest("VI011","Passenger",21,"Male","DL","AP0920181234567");
		request.setUsername(user.getUsername());
		Ticket ticket = this.flightService.bookFlight(request);
		assertNotNull(ticket);	
		
	}
	
	@Test
	@DisplayName("Booking Ticket With Right Passport Credentials")
	@Rollback(true)
	public void bookTicketWithRightPassport() throws Exception {
		User user = new User("Sample User","sample1@gmail.com","Password@123",12,"Male");
		this.userService.registerUser(user);
		BookFlightRequest request = new BookFlightRequest("VI011","Passenger",21,"Male","Passport","A1234567");
		request.setUsername(user.getUsername());
		Ticket ticket = this.flightService.bookFlight(request);
		assertNotNull(ticket);	
		
	}
}
