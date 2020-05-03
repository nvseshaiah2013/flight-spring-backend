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
@Table(name = "PASSENGER_MASTER")
public class Passenger implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int passenger_id;

    @Column(name="ID_TYPE",length = 15)
    @NotNull(message="Type Of Id Cannot be Omitted")
    @NotEmpty(message="Type of Id Cannot be Left Empty")
    @Pattern(regexp="PAN|DL|Passport|Aadhar",message="Accepted Id Types: PAN,Aadhar,Passport,Driving License")
    private String idType;

    @Column(name="ID_NO",length = 30)
    @NotNull(message = "Id Number Cannot be Omitted")
    @NotEmpty(message = "Id Number Cannot be Left Empty")
    private String idNo;

    @Column(name="PASSENGER_NAME",length = 50)
    @NotNull(message = "Name Cannot be Omitted")
    @NotEmpty(message = "Name Cannot be Left Empty")
    @Pattern(regexp="([a-zA-Z]+[ ]?)+",message="Accepted Name can only contain alphabets, with single space separeted.")
    private String name;

    @Column(name="PASSENGER_AGE")
    @NotNull(message = "Age Cannot be Omitted")
    @Min(value=5,message="Age must be greater than or equal to 5")
    @Max(value=122 ,message="Age must be less than or equal to 122")
    private int age;

    @Column(name="PASSENGER_GENDER",length = 6)
    @NotNull(message = "Gender Cannot be Omitted")
    @NotEmpty(message = "Gender Cannot be Left Empty")
    @Pattern(regexp = "Male|Female|Other",message = "Gender can be Male,Female,Other only")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "username",nullable=false)
    private User user;
   
    
    public Passenger(
			@NotNull(message = "Type Of Id Cannot be Omitted") @NotEmpty(message = "Type of Id Cannot be Left Empty") @Pattern(regexp = "PAN|DL|Passport|Aadhar") String idType,
			@NotNull(message = "Id Number Cannot be Omitted") @NotEmpty(message = "Id Number Cannot be Left Empty") String idNo,
			@NotNull(message = "Name Cannot be Omitted") @NotEmpty(message = "Name Cannot be Left Empty") String name,
			@NotNull(message = "Age Cannot be Omitted") @Min(value = 5, message = "Age must be greater than or equal to 5") @Max(value = 122, message = "Age must be less than or equal to 122") int age,
			@NotNull(message = "Gender Cannot be Omitted") @NotEmpty(message = "Gender Cannot be Left Empty") @Pattern(regexp = "Male|Female|Other", message = "Gender can be Male,Female,Other only") String gender,
			User user) {
		this.idType = idType;
		this.idNo = idNo;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.user = user;
	}
	public Passenger(
			@NotNull(message = "Type Of Id Cannot be Omitted") @NotEmpty(message = "Type of Id Cannot be Left Empty") @Pattern(regexp = "PAN|DL|Passport|Aadhar") String idType,
			@NotNull(message = "Id Number Cannot be Omitted") @NotEmpty(message = "Id Number Cannot be Left Empty") String idNo,
			@NotNull(message = "Name Cannot be Omitted") @NotEmpty(message = "Name Cannot be Left Empty") String name,
			@NotNull(message = "Age Cannot be Omitted") @Min(value = 5, message = "Age must be greater than or equal to 5") @Max(value = 122, message = "Age must be less than or equal to 122") int age,
			@NotNull(message = "Gender Cannot be Omitted") @NotEmpty(message = "Gender Cannot be Left Empty") @Pattern(regexp = "Male|Female|Other", message = "Gender can be Male,Female,Other only") String gender) {
		this.idType = idType;
		this.idNo = idNo;
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	public String getIdType() {
        return idType;
    }

    public void setIdType(final String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(final String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public Passenger(){
        
    }   
    public int getPassenger_id() {
        return passenger_id;
    }

    public void setPassenger_id(final int passenger_id) {
        this.passenger_id = passenger_id;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Passenger [age=" + age + ", gender=" + gender + ", idNo=" + idNo + ", idType=" + idType + ", name="
                + name + ", passenger_id=" + passenger_id + ", user=" + user + "]";
    }    
}