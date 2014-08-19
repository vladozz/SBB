package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Passenger;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class PassengerDAO extends CommonDAO<Passenger> {
    public Passenger findByNameAndBirthDate(String firstName, String lastName, Date birthDate) {
        String queryString = "SELECT p FROM Passenger p WHERE LOWER(p.firstName) = :firstName AND " +
                "LOWER(p.lastName) = :lastName AND p.birthDate = :birthDate";
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
}
