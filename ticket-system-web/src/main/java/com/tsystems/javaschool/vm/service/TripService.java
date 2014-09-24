package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.TrainDAO;
import com.tsystems.javaschool.vm.dao.TripDAO;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.exception.EmptyListException;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
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

    public Trip findById(Long tripId) throws EntityNotFoundException {
        return tripDAO.findById(tripId);
    }

    @Transactional
    public Trip addTrip(Long pathId, Long trainId) throws SBBException {
        Path path = pathDAO.findById(pathId);
        Train train = trainDAO.findById(trainId);
        if (path.getStations().size() < 2) {
            throw new EmptyListException("You can't create trip with empty list of stations. Path:" + path);
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
    public Trip editTrip(Long tripId, Long pathId, Long trainId, Integer lci) throws OutdateException, EntityNotFoundException {
        Trip trip = tripDAO.findById(tripId);
        Path path = pathDAO.findById(pathId);
        Train train = trainDAO.findById(trainId);

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

