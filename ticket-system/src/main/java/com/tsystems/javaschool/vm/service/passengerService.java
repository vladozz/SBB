package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.*;
import com.tsystems.javaschool.vm.domain.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PassengerService {
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

    public boolean canBuyTicket(Passenger passenger, Board departure, Board arrive) throws Exception {
        final long TEN_MINUTES = 1000L * 60 * 10;
        if (countFreePlacesOfTrip(departure, arrive) <= 0) {
            throw new Exception("Out of free spaces");
        }
        if (isPassengerOnTrip(passenger, departure.getTrip())) {
            throw new Exception("Passenger has already bought ticket on this trip");
        }
        if (departure.getDepartureTime().getTime() - (new Date()).getTime() < TEN_MINUTES ) {
            throw new Exception("Less than ten minutes before train departure");
        }
        return true;
    }

    public Ticket buyTicket(Passenger passenger, Board departure, Board arrive) throws Exception {
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
