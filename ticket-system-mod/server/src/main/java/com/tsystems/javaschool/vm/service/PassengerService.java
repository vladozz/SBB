package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.*;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.exception.AlreadyOnTripException;
import com.tsystems.javaschool.vm.exception.InvalidIdException;
import com.tsystems.javaschool.vm.exception.OutOfFreeSpacesException;
import com.tsystems.javaschool.vm.exception.TenMinutesException;
import org.apache.log4j.Logger;

import javax.persistence.EntityTransaction;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PassengerService {
    private static Logger logger = Logger.getLogger(PassengerService.class);
    private TicketDAO ticketDAO;
    private PassengerDAO passengerDAO;
    private StationDAO stationDAO;
    private BoardDAO boardDAO;
    private TripDAO tripDAO;

    public PassengerService(PassengerDAO passengerDAO, TicketDAO ticketDAO, BoardDAO boardDAO, StationDAO stationDAO, TripDAO tripDAO){
        this.ticketDAO = ticketDAO;
        this.passengerDAO = passengerDAO;
        this.stationDAO = stationDAO;
        this.boardDAO = boardDAO;
        this.tripDAO = tripDAO;
    }

    public Passenger addPassenger(String firstName, String lastName, Calendar birthDate) {
        Passenger passenger = new Passenger(firstName, lastName, birthDate);
        EntityTransaction trx = passengerDAO.getTransaction();
        try {
            trx.begin();
            passengerDAO.create(passenger);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
                return null;
            }
        }
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
        Passenger passenger = passengerDAO.findByNameAndBirthDate(firstName, lastName, birthDate);
        if (passenger == null) {
            passenger = addPassenger(firstName, lastName, birthDate);
            if (passenger == null) {
                logger.warn("Unable to create passenger:" + " firstName = " + firstName +
                        " lastName = " + lastName + " birthDate = " + birthDate);
                return null;
            }
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
                    return null;
                }
            }
            return ticket;
        }
        return null;
    }



    public List<Passenger> getPassengersOfTripById(Long tripId) {
        Trip trip = tripDAO.findById(tripId);
        if (trip == null) {
            return null;
        }
        return passengerDAO.getPassengersOfTrip(trip);
    }

}
