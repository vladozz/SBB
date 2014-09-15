package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.dao.TrainDAO;
import com.tsystems.javaschool.vm.dao.TripDAO;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.exception.InvalidIdException;
import com.tsystems.javaschool.vm.exception.OutdateException;
import com.tsystems.javaschool.vm.exception.SBBException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TripService {
    private static final Logger logger = Logger.getLogger(TripService.class);
    @Autowired
    private PathDAO pathDAO;
    @Autowired
    private TrainDAO trainDAO;
    @Autowired
    private TripDAO tripDAO;

    public TripService() {
    }

    public Trip findById(Long tripId) {
        return tripDAO.findById(tripId);
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
        trip.setLastChange(1);
        tripDAO.create(trip);
        return trip;
    }

    private boolean checkLCI(Trip trip, Integer tripLCI) {
        if (!tripLCI.equals(trip.getLastChange())) {
            return false;
        } else {
            trip.incrementLastChange();
            return true;
        }
    }

    @Transactional
    public Trip editTrip(Long tripId, Long pathId, Long trainId, Integer lci) throws OutdateException {
        Trip trip = tripDAO.findById(tripId);
        Path path = pathDAO.findById(pathId);
        Train train = trainDAO.findById(trainId);
        if (trip == null) {
            String message = "Trip doesn't exist tripId = " + tripId;
            throw new IllegalArgumentException(message);
        }
        if (train == null || path == null) {
            String message = "Path or train doesn't exist pathId = " + pathId + " trainId = " + trainId;
            throw new IllegalArgumentException(message);
        }
        if (!checkLCI(trip, lci)) {
            throw new OutdateException("LCI in request: " + lci + "; LCI in database: " + trip.getLastChange());
        }
        trip.setPath(path);
        trip.setTrain(train);
        tripDAO.update(trip);
        return trip;
    }

    @Transactional
    public void removeTrip(Long tripId, Integer lci) throws SBBException {
        Trip trip = tripDAO.findById(tripId);
        if (trip == null) {
            throw new InvalidIdException("Trip with id '" + tripId + "' doesn't exist.");
        }
        if (!checkLCI(trip, lci)) {
            throw new OutdateException("LCI in request: " + lci + "; LCI in database: " + trip.getLastChange());
        }
        tripDAO.delete(tripId);
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

