package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Trip;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class TripDAO extends CommonDAO<Trip> {

    public TripDAO() {
        super(Trip.class);
    }

    public List<Trip> filterTrips(Long pathId, Long trainId) {
        String queryString = "Select t from Trip t WHERE t.train.id = :trainId and t.path.id = :pathId and t.deleted = false";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trainId", trainId);
        query.setParameter("pathId", pathId);
        return query.getResultList();
    }

    public List<Trip> filterTripsByTrain(Long trainId) {
        String queryString = "Select t from Trip t WHERE t.train.id = :trainId and t.deleted = false";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trainId", trainId);
        return query.getResultList();
    }

    public List<Trip> filterTripsByPath(Long pathId) {
        String queryString = "Select t from Trip t WHERE t.path.id = :pathId and t.deleted = false";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("pathId", pathId);
        return query.getResultList();
    }

}
