package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Passenger;
import com.tsystems.javaschool.vm.domain.Trip;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

@Component
public class PassengerDAO extends CommonDAO<Passenger> {

    public PassengerDAO() {
        super(Passenger.class);
    }

    public List<Passenger> findByNameAndBirthDate(String firstName, String lastName, Calendar birthDate) {
        String queryString = "SELECT p FROM Passenger p WHERE LOWER(p.firstName) = :firstName AND " +
                "LOWER(p.lastName) = :lastName AND year(p.birthDate) = year(:birthDate) " +
                "and month(p.birthDate) = month(:birthDate) and day(p.birthDate) = day(:birthDate)";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("firstName", firstName.toLowerCase());
        query.setParameter("lastName", lastName.toLowerCase());
        query.setParameter("birthDate", birthDate);

        return query.getResultList();
    }

    public List<Passenger> getPassengersOfTrip(Trip trip) {
        String queryString = "SELECT DISTINCT p FROM Passenger p INNER JOIN p.tickets t WHERE t.arrive.trip = :trip";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trip", trip);
        return query.getResultList();
    }

}
