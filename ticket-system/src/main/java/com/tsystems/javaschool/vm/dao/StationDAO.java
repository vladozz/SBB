package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.Trip;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.TimeZone;

public class StationDAO extends CommonDAO<Station> {
    public StationDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Station> findByName(String title) {
        String queryString = "SELECT s FROM Station s WHERE LOWER(s.title) = :title";
        Query query = em.createQuery(queryString);
        query.setParameter("title", title.toLowerCase());
        return query.getResultList();
    }

    public List<Station> findByGMT(TimeZone timeZone) {
        String queryString = "SELECT s FROM Station s WHERE LOWER(s.timeZone) = :timeZone";
        Query query = em.createQuery(queryString);
        query.setParameter("timeZone", timeZone);
        return query.getResultList();
    }

    public List<Station> getFromBoardByTrip(Trip trip) {
        String queryString = "SELECT b.station FROM Board b WHERE b.trip = :trip";
        Query query = em.createQuery(queryString);
        query.setParameter("trip", trip);
        return query.getResultList();
    }
}
