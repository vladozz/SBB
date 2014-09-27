package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Ticket;
import com.tsystems.javaschool.vm.domain.Trip;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class TicketDAO extends CommonDAO<Ticket> {

    public TicketDAO() {
        super(Ticket.class);
    }

    /**
     *
     * @param trip - рейс
     * @return список всех купленных билетов на определенный рейс
     */
    public List<Ticket> getTicketsOfTrip(Trip trip) {
        String queryString = "SELECT DISTINCT t FROM Ticket t WHERE t.arrive.trip = :trip";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trip", trip);
        return query.getResultList();
    }

    public List<Ticket> getTicketsOfTrip(Long tripId) {
        String queryString = "SELECT DISTINCT t FROM Ticket t WHERE t.arrive.trip.id = :tripId";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("tripId", tripId);
        return query.getResultList();
    }

    public List<Ticket> getTicketsOfTripByPassenger(Trip trip, Long passengerId) {
        String queryString = "SELECT DISTINCT t FROM Ticket t WHERE t.arrive.trip = :trip AND t.passenger.id = :passengerId";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trip", trip);
        query.setParameter("passengerId", passengerId);
        return query.getResultList();
    }
}
