package com.cg.flight.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.flight.entities.User;
import com.cg.flight.requests.LoginRequest;
import com.cg.flight.responses.LoginResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AppServiceTest {

private static Logger logger;
	
	@Autowired
	private IUserService userService;

	@BeforeAll
	static void setUpBeforeClass() {
		logger = LoggerFactory.getLogger(AppServiceTest.class);
		System.out.println("Fetching resources for testing ...");
	}

	@BeforeEach
	void setup() {
		logger.info("Test Case Started");
		System.out.println("Test Case Started");
	}
	
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
			System.out.println(user.getPassword());
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

	@AfterEach
	void tearDown() {
		logger.info("Test Case Over");
		System.out.println("Test Case Over");
	}
	
	
}
