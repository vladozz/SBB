package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.*;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.exception.AlreadyOnTripException;
import com.tsystems.javaschool.vm.exception.InvalidIdException;
import com.tsystems.javaschool.vm.exception.OutOfFreeSpacesException;
import com.tsystems.javaschool.vm.exception.TenMinutesException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityTransaction;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PassengerService {
    private static final Logger LOGGER = Logger.getLogger(PassengerService.class);

    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private PassengerDAO passengerDAO;
    @Autowired
    private StationDAO stationDAO;
    @Autowired
    private BoardDAO boardDAO;
    @Autowired
    private TripDAO tripDAO;

    public PassengerService() {
    }

    @Transactional
    public Passenger addPassenger(String firstName, String lastName, Calendar birthDate) {
        Passenger passenger = new Passenger(firstName, lastName, birthDate);
        passengerDAO.create(passenger);
        return passenger;
    }

    /**
     * Метод, возвращащий кол-во доступных для покупки мест на рейс между желаемыми станциями отправления и прибытия
     * @param departure
     * @param arrive
     * @return кол-во доступных для покупки мест
     */
    public int countFreePlacesOfTrip(Board departure, Board arrive) {
        Trip trip = departure.getTrip();
        List<Station> stations = stationDAO.getFromBoardByTrip(trip);
        List<Ticket> tickets = ticketDAO.getTicketsOfTrip(trip);

        int begin = stations.indexOf(departure.getStation());
        int end = stations.indexOf(arrive.getStation());
        int max = 0;
        int[] fillness = new int[end - begin];
        for (int i = begin; i < end; i++) {
            int c = 0;
            //todo: optimize
            for (Ticket t : tickets) {
                if (stations.indexOf(t.getDeparture().getStation()) <= i &&
                        stations.indexOf(t.getArrive().getStation()) >= i + 1) {
                    c++;
                }
            }
            fillness[i - begin] = c;
            if (c > max) {
                max = c;
            }
        }
        return trip.getTrain().getPlacesQty() - max;
    }

    public boolean isPassengerOnTrip(Passenger passenger, Trip trip) {
        List<Ticket> tickets = ticketDAO.getTicketsOfTrip(trip);

        for (Ticket t : tickets) {
            if (t.getPassenger().equals(passenger)) {
                return true;
            }
        }
        return false;
    }

    public boolean canBuyTicket(Passenger passenger, Board departure, Board arrive)
            throws OutOfFreeSpacesException, AlreadyOnTripException, TenMinutesException {
        final long TEN_MINUTES = 1000L * 60 * 10;
        if (departure.getDepartureTime().getTime() - (new Date()).getTime() < TEN_MINUTES ) {
            throw new TenMinutesException("Less than ten minutes before train departure");
        }
        if (isPassengerOnTrip(passenger, departure.getTrip())) {
            throw new AlreadyOnTripException("Passenger has already bought ticket on this trip");
        }
        if (countFreePlacesOfTrip(departure, arrive) <= 0) {
            throw new OutOfFreeSpacesException("Out of free spaces");
        }
        return true;
    }

    public Ticket buyTicket(String firstName, String lastName, Calendar birthDate, Long departureBoardId, Long arriveBoardId)
            throws InvalidIdException, OutOfFreeSpacesException, TenMinutesException, AlreadyOnTripException {
        List<Passenger> passengers = passengerDAO.findByNameAndBirthDate(firstName, lastName, birthDate);
        Passenger passenger;
        if (passengers.isEmpty()) {
            passenger = addPassenger(firstName, lastName, birthDate);
            if (passenger == null) {
                LOGGER.warn("Unable to create passenger:" + " firstName = " + firstName +
                        " lastName = " + lastName + " birthDate = " + birthDate);
                return null;
            }
        } else {
            passenger = passengers.get(0);
        }
        Board departureBoard = boardDAO.findById(departureBoardId);
        Board arriveBoard = boardDAO.findById(arriveBoardId);
        if (departureBoard == null) {
            throw new InvalidIdException("Departure BoardId doesn't exist");
        }
        if (arriveBoard == null) {
            throw new InvalidIdException("Arrive BoardId doesn't exist");
        }
        return buyTicket(passenger, departureBoard, arriveBoard);

    }

    public Ticket buyTicket(Passenger passenger, Board departure, Board arrive)
            throws OutOfFreeSpacesException, TenMinutesException, AlreadyOnTripException {
        if (canBuyTicket(passenger, departure, arrive)) {
            Ticket ticket = new Ticket(passenger, departure, arrive);
            EntityTransaction trx = ticketDAO.getTransaction();
            try {
                trx.begin();
                ticketDAO.create(ticket);
                trx.commit();
            } finally {
                if (trx.isActive()) {
                    trx.rollback();
                    ticket = null;
                }
            }
            return ticket;
        }
        return null;
    }



    public List<Passenger> getPassengersByTrip(Long tripId) {
        Trip trip = tripDAO.findById(tripId);
        if (trip == null) {
            return null;
        } else {
            return passengerDAO.getPassengersOfTrip(trip);
        }
    }

}
