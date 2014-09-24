package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.*;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.dto.BuyTicketDTO;
import com.tsystems.javaschool.vm.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PassengerService {
    //private static final Logger LOGGER = Logger.getLogger(PassengerService.class);

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
     *
     * @param departure
     * @param arrive
     * @return кол-во доступных для покупки мест
     */
    public int countFreePlaces(Board departure, Board arrive) {
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
            throws PassengerException {
        final long TEN_MINUTES = 1000L * 60 * 10;
        if (departure.getDepartureTime().getTime() - (new Date()).getTime() < TEN_MINUTES) {
            throw new PassengerException("Less than ten minutes before train departure");
        }
        if (isPassengerOnTrip(passenger, departure.getTrip())) {
            throw new PassengerException("Passenger has already bought ticket on this trip");
        }
        if (countFreePlaces(departure, arrive) <= 0) {
            throw new PassengerException("Out of free spaces");
        }
        return true;
    }

    @Transactional
    public Ticket buyTicket(BuyTicketDTO buyTicketDTO) throws EntityNotFoundException, PassengerException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(buyTicketDTO.getBirthDate());
        return buyTicket(buyTicketDTO.getFirstName(), buyTicketDTO.getLastName(), calendar,
                buyTicketDTO.getDepartureBoardId(), buyTicketDTO.getArriveBoardId());
    }

    @Transactional
    public Ticket buyTicket(String firstName, String lastName, Calendar birthDate, Long departureBoardId, Long arriveBoardId)
            throws EntityNotFoundException, PassengerException {
        List<Passenger> passengers = passengerDAO.findByNameAndBirthDate(firstName, lastName, birthDate);
        Passenger passenger;
        if (passengers.isEmpty()) {
            passenger = addPassenger(firstName, lastName, birthDate);
        } else {
            passenger = passengers.get(0);
        }
        Board departureBoard = boardDAO.findById(departureBoardId);
        Board arriveBoard = boardDAO.findById(arriveBoardId);

        return buyTicket(passenger, departureBoard, arriveBoard);

    }

    public Ticket buyTicket(Passenger passenger, Board departure, Board arrive)
            throws PassengerException {

        //throws PassengerException if false
        canBuyTicket(passenger, departure, arrive);

        Ticket ticket = new Ticket(passenger, departure, arrive);
        ticketDAO.create(ticket);
        return ticket;
    }

    public List<Passenger> getPassengersByTrip(Long tripId) throws EntityNotFoundException {
        Trip trip = tripDAO.findById(tripId);

        return passengerDAO.getPassengersOfTrip(trip);

    }

    public List<Ticket> getTicketsOfTrip(Long tripId) {
        return ticketDAO.getTicketsOfTrip(tripId);
    }

}
