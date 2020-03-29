package com.cg.flight.requests;

import org.springframework.stereotype.Component;

@Component
public class BookFlightRequest {
	private String username;
	private String flight_code;
	private String name;
	private int age;
	private String gender;
	private String idType;
	private String idNo;
	
	
	public BookFlightRequest()
	{
		
	}	
	
	public BookFlightRequest(String name, int age, String gender,String idType, String idNo,String flight_code) {
		this.flight_code = flight_code;
		this.name = name;
		this.age = age;
		this.idType = idType;
		this.idNo = idNo;
		this.gender = gender;
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
