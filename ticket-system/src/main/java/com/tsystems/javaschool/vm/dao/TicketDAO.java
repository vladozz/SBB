package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Ticket;
import com.tsystems.javaschool.vm.domain.Trip;

import javax.persistence.Query;
import java.util.List;

public class TicketDAO extends CommonDAO<Ticket> {
    /**
     *
     * @param trip - рейс
     * @return список всех купленных билетов на определенный рейс
     */
    public List<Ticket> getTicketsOfTrip(Trip trip) {
        String queryString = "SELECT DISTINCT t FROM Ticket t WHERE t.arrive.trip = :trip";
        Query query = em.createQuery(queryString);
        query.setParameter("trip", trip);
        return query.getResultList();
    }
}
