package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Trip;

import javax.persistence.EntityManager;

public class TripDAO extends CommonDAO<Trip> {
    public TripDAO(EntityManager entityManager) {
        super(entityManager);
    }
}
