package com.cg.flight.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class BookFlightRequest {
	
	private String username;
	@NotNull(message = "Flight Code is Missing")
	@NotEmpty(message = "Flight Code is left Empty")
	private String flight_code;

	@NotNull(message = "Passenger Name is Missing")
	@NotEmpty(message = "Passenger Name is left Empty")
	@Pattern(regexp="([a-zA-Z]+[ ]?)+",message="Name can only contain alphabets, Single space separeted only.")
	private String name;

	@NotNull(message = "Passenger Age is Missing")
	@Min(value = 5, message = "Passenger Age cannot be less than 5")
	@Max(value = 122, message = "Passenger Age cannot be more than 122")
	private int age;

	@NotNull(message = "Passenger Gender is Missing")
	@NotEmpty(message = "Passenger Gender is left Empty")
	@Pattern(regexp ="Male|Female|Other")
	private String gender;

	@NotNull(message = "Passenger Id Type is Missing")
	@NotEmpty(message = "Passenger Id Type is left Empty")
	@Pattern(regexp = "PAN|DL|Passport|Aadhar")
	private String idType;

	@NotNull(message = "Passenger Id Number is Missing")
	@NotEmpty(message = "Passenger Id Number is left Empty")
	private String idNo;
	
	
	public BookFlightRequest()
	{
		
	}	
	
	public BookFlightRequest(
			@NotNull(message = "Flight Code is Missing") @NotEmpty(message = "Flight Code is left Empty") String flight_code,
			@NotNull(message = "Passenger Name is Missing") @NotEmpty(message = "Passenger Name is left Empty") String name,
			@NotNull(message = "Passenger Age is Missing") @Min(value = 5, message = "Passenger Age cannot be less than 5") @Max(value = 122, message = "Passenger Age cannot be more than 122") int age,
			@NotNull(message = "Passenger Gender is Missing") @NotEmpty(message = "Passenger Gender is left Empty") @Pattern(regexp = "Male|Female|Other") String gender,
			@NotNull(message = "Passenger Id Type is Missing") @NotEmpty(message = "Passenger Id Type is left Empty") @Pattern(regexp = "PAN|DL|Passport|Aadhar") String idType,
			@NotNull(message = "Passenger Id Number is Missing") @NotEmpty(message = "Passenger Id Number is left Empty") String idNo) {
		this.flight_code = flight_code;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.idType = idType;
		this.idNo = idNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFlight_code() {
		return flight_code;
	}
	public void setFlight_code(String flight_code) {
		this.flight_code = flight_code;
	}

	@Override
	public String toString() {
		return "BookFlightRequest [username=" + username + ", flight_code=" + flight_code + ", name=" + name + ", age="
				+ age + ", gender=" + gender + ", idType=" + idType + ", idNo=" + idNo + "]";
	}

	
	
	
}
