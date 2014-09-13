package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.dao.TrainDAO;
import com.tsystems.javaschool.vm.dao.TripDAO;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.domain.Trip;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TripService {
    private static Logger logger = Logger.getLogger(TripService.class);
    @Autowired
    private PathDAO pathDAO;
    @Autowired
    private StationDAO stationDAO;
    @Autowired
    private TrainDAO trainDAO;
    @Autowired
    private TripDAO tripDAO;

    public TripService() {
    }

    @Transactional
    public Trip addTrip(Long pathId, Long trainId) {
        Path path = pathDAO.findById(pathId);
        Train train = trainDAO.findById(trainId);
        if (train == null || path == null) {
            String message = "Path or train doesn't exist pathId = " + pathId + " trainId = " + trainId;
            throw new IllegalArgumentException(message);
        }
        Trip trip = new Trip(path, train);
        tripDAO.create(trip);
        return trip;
    }

    public List<Trip> getTripsByPathAndTrain(Long pathId, Long trainId) {
        if (pathId != 0 && trainId != 0) {
            return tripDAO.filterTrips(pathId, trainId);
        } else if (trainId != 0) {
            return tripDAO.filterTripsByTrain(trainId);
        } else if (pathId != 0) {
            return tripDAO.filterTripsByPath(pathId);
        } else {
            return tripDAO.findAll();
        }
    }
}

