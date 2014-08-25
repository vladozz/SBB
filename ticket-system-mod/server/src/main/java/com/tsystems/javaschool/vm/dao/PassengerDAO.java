package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Passenger;
import com.tsystems.javaschool.vm.domain.Trip;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

public class PassengerDAO extends CommonDAO<Passenger> {
    public PassengerDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Passenger findByNameAndBirthDate(String firstName, String lastName, Calendar birthDate) {
        String queryString = "SELECT p FROM Passenger p WHERE LOWER(p.firstName) = :firstName AND " +
                "LOWER(p.lastName) = :lastName AND year(p.birthDate) = year(:birthDate) " +
                "and month(p.birthDate) = month(:birthDate) and day(p.birthDate) = day(:birthDate)";
        Query query = em.createQuery(queryString);
        query.setParameter("firstName", firstName.toLowerCase());
        query.setParameter("lastName", lastName.toLowerCase());
        query.setParameter("birthDate", birthDate);

        List<Passenger> passenger = query.getResultList();
        if (passenger.size() == 0) {
            return null;
        } else {
            return passenger.get(0);
        }
    }

    public List<Passenger> getPassengersOfTrip(Trip trip) {
        String queryString = "SELECT DISTINCT p FROM Passenger p INNER JOIN p.tickets t WHERE t.arrive.trip = :trip";
        Query query = em.createQuery(queryString);
        query.setParameter("trip", trip);
        return query.getResultList();
    }
}
