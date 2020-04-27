package com.cg.flight.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.cg.flight.entities.Passenger;
import com.cg.flight.services.IService;
import com.cg.flight.services.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "http://localhost:4200",allowedHeaders = "*")
@RequestMapping(value="/passengers")
public class PassengerController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IService passengerService;
    
    /*
     * Function to Get the List of Passengers Searches for all passengers belonging
     * the user identified by the username Can Throw UserNotFound Exception On
     * Success returns a List of Passengers
     */
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPassengers(HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        final String username = jwtUtil.extractUsername(token.substring(7));
        List<Passenger> passengers = passengerService.getPassengers(username);
        return new ResponseEntity<Object>(passengers, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addPassenger(HttpServletRequest request, @Valid @RequestBody Passenger passenger)
            throws Exception {
        final String token = request.getHeader("Authorization");
        final String username = jwtUtil.extractUsername(token.substring(7));
        this.passengerService.addPassenger(passenger, username);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updatePassenger(HttpServletRequest request, @Valid @RequestBody Passenger passenger) throws Exception {
        final String token = request.getHeader("Authorization");
        final String username = jwtUtil.extractUsername(token.substring(7));
        this.passengerService.updatePassenger(passenger, username,passenger.getPassenger_id());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deletePassenger(HttpServletRequest request, @PathVariable("id") int id)
            throws Exception {
        final String token = request.getHeader("Authorization");
        final String username = jwtUtil.extractUsername(token.substring(7));
        this.passengerService.deletePassenger(id, username);
        return ResponseEntity.ok().build();
    }

}