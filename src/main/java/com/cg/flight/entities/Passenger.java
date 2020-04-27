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
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "passenger_master")
public class Passenger implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int passenger_id;

    @Column(length = 15)
    @NotNull(message="Type Of Id Cannot be Omitted")
    @NotEmpty(message="Type of Id Cannot be Left Empty")
    private String idType;

    @Column(length = 30)
    @NotNull(message = "Id Number Cannot be Omitted")
    @NotEmpty(message = "Id Number Cannot be Left Empty")
    private String idNo;

    @Column(length = 30)
    @NotNull(message = "Name Cannot be Omitted")
    @NotEmpty(message = "Name Cannot be Left Empty")
    private String name;

    @Column(length = 15)
    @NotNull(message = "Age Cannot be Omitted")
    @Min(value=5,message="Age must be greater than 5")
    private int age;

    @Column(length = 6)
    @NotNull(message = "Gender Cannot be Omitted")
    @NotEmpty(message = "Gender Cannot be Left Empty")
    @Pattern(regexp = "Male|Female|Other",message = "Gender can be Male,Female,Other only")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @Column(columnDefinition = "integer default 1")
    @NotNull
    private int isValid;
    

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

    public Passenger(
            @NotNull(message = "Type Of Id Cannot be Omitted") @NotEmpty(message = "Type of Id Cannot be Left Empty") String idType,
            @NotNull(message = "Id Number Cannot be Omitted") @NotEmpty(message = "Id Number Cannot be Left Empty") String idNo,
            @NotNull(message = "Name Cannot be Omitted") @NotEmpty(message = "Name Cannot be Left Empty") String name,
            @NotNull(message = "Age Cannot be Omitted") @Min(value = 5, message = "Age must be greater than 5") int age,
            @NotNull(message = "Gender Cannot be Omitted") @NotEmpty(message = "Gender Cannot be Left Empty") @Pattern(regexp = "Male|Female|Other", message = "Gender can be Male,Female,Other only") String gender,
            int isValid) {
        this.idType = idType;
        this.idNo = idNo;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.isValid = isValid;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

 

    
}