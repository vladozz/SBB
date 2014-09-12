package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Trip;
import org.springframework.stereotype.Component;

@Component
public class TripDAO extends CommonDAO<Trip> {

    public TripDAO() {
        super(Trip.class);
    }
}
