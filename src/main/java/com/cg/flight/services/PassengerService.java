package com.cg.flight.services;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.flight.dao.IPassengerDao;
import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.User;
import com.cg.flight.exceptions.InvalidPassengerException;
import com.cg.flight.exceptions.NotFound;

@Service
public class PassengerService implements IPassengerService {

	private static Logger logger = LoggerFactory.getLogger(PassengerService.class);

	@Autowired
	IPassengerDao passengerRepo;

	@Autowired
	IUserService userService;

	@Override
	public Passenger findPassengerById(int id) throws Exception {
		Passenger passenger = passengerRepo.findPassengerById(id);
		if (passenger == null) {
			logger.error("Passenger with Id " + id + " not Found ");
			throw new NotFound("Passenger Not Found");
		}
		return passenger;
	}

	@Override
	public Set<Passenger> getPassengers(String username) throws Exception {
		User user = this.userService.findById(username);
		return passengerRepo.getPassengers(user);
	}

	@Override
	@Transactional
	public void updatePassenger(Passenger passenger, String username, int id) throws Exception {
		if (passenger.getIdType().equals("PAN")) {
			if (!passenger.getIdNo().matches("[A-Z]{5}[0-9]{4}[A-Z]")) {
				logger.error("PAN No." + passenger.getIdNo() + " is Invalid. Permitted Format: ABCDE1234A");
				throw new InvalidPassengerException("PAN No. is Invalid. Permitted Format: ABCDE1234A");
			}
		} else if (passenger.getIdType().equals("Aadhar")) {
			if (!passenger.getIdNo().matches("[2-9][0-9]{11}")) {
				logger.error("Aadhar No. " + passenger.getIdNo() + " is Invalid. Permitted Format: 201234567890");
				throw new InvalidPassengerException("Aadhar No. is Invalid. Permitted Format: 201234567890");
			}
		} else if (passenger.getIdType().equals("DL")) {
			if (!passenger.getIdNo().matches(
					"((A[NPRS])|(BR)|(C[GH])|(D[LD])|(G[AJ])|(H[RP])"
					+ "|(J[KH])|"
					+ "(K[LA])|(L[AD])|(M[PHNLZ])"
					+ "|(NL)|(OD)|(P[BY])|(RJ)|(SK)|(T[NSR])|(U[PK])|(WB))"
					+ "([0-9][1-9])(20(([0-1][0-9])|(20)))([0-9]{6}[1-9])"))

				{logger.error(
						"Driving License " + passenger.getIdNo() + " is Invalid. Permitted Format: MH0220201234567");
			throw new InvalidPassengerException("Driving License is Invalid. Permitted Format: MH0220201234567");}
		} else if (passenger.getIdType().equals("Passport")) {
			if (!passenger.getIdNo().matches("[A-Z][0-9]{7}")) {
				logger.error("Passport is " + passenger.getIdNo() + "Invalid. Permitted Format: A1234567 ");
				throw new InvalidPassengerException("Passport is Invalid. Permitted Format: A1234567 ");
			}
		}
		Passenger passenger_old = this.findPassengerById(id);
		if (!passenger_old.getUser().getUsername().equals(username)) {
			logger.error("Passenger Not Found for Given User" + username);
			throw new NotFound("Passenger Not Found for Given User");
		}
		passengerRepo.updatePassenger(passenger, passenger_old);
	}

	@Override
	@Transactional
	public void addPassenger(Passenger passenger, String username) throws Exception {
		User user = userService.findById(username);
		if (passenger.getIdType().equals("PAN")) {
			if (!passenger.getIdNo().matches("[A-Z]{5}[0-9]{4}[A-Z]")) {
				logger.error("PAN No." + passenger.getIdNo() + " is Invalid. Permitted Format: ABCDE1234A");
				throw new InvalidPassengerException("PAN No. is Invalid. Permitted Format: ABCDE1234A");
			}
		} else if (passenger.getIdType().equals("Aadhar")) {
			if (!passenger.getIdNo().matches("[2-9][0-9]{11}")) {
				logger.error("Aadhar No. " + passenger.getIdNo() + " is Invalid. Permitted Format: 201234567890");
				throw new InvalidPassengerException("Aadhar No. is Invalid. Permitted Format: 201234567890");
			}
		} else if (passenger.getIdType().equals("DL")) {
			if (!passenger.getIdNo().matches(
					"((A[NPRS])|(BR)|(C[GH])|(D[LD])|(G[AJ])|(H[RP])|(J[KH])|"
					+ "(K[LA])|(L[AD])|(M[PHNLZ])|"
					+ "(NL)|(OD)|(P[BY])|(RJ)|(SK)|(T[NSR])|(U[PK])|(WB))"
					+ "([0-9][1-9])(20(([0-1][0-9])|(20)))([0-9]{6}[1-9])")) {

				logger.error(
						"Driving License " + passenger.getIdNo() + " is Invalid. Permitted Format: MH0220201234567");
			
			throw new InvalidPassengerException("Driving License is Invalid. Permitted Format: MH0220201234567");}
		} else if (passenger.getIdType().equals("Passport")) {
			if (!passenger.getIdNo().matches("[A-Z][0-9]{7}")) {
				logger.error("Passport is " + passenger.getIdNo() + "Invalid. Permitted Format: A1234567 ");
				throw new InvalidPassengerException("Passport is Invalid. Permitted Format: A1234567 ");
			}
		}
		this.passengerRepo.addPassenger(passenger, user);

	}

	@Override
	@Transactional
	public void deletePassenger(int id, String username) throws Exception {
		User user = userService.findById(username);
		Passenger passenger = findPassengerById(id);
		if (!passenger.getUser().getUsername().equals(user.getUsername())) {
			logger.error("Passenger Not Found for Given User" + user.getUsername());
			throw new NotFound("Passenger Not Found for Given User");
		}
		logger.info("Passenger with id " + id + " deletion started");
		this.passengerRepo.deletePassenger(passenger);
	}
}
