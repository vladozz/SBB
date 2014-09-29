package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.BoardDAO;
import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.TrainDAO;
import com.tsystems.javaschool.vm.dao.TripDAO;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.exception.*;
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
    @Autowired
    private BoardDAO boardDAO;
    @Autowired
    private PassengerService passengerService;

    public TripService() {
    }

    public Trip findById(Long tripId) throws EntityNotFoundException {
        return tripDAO.findById(tripId);
    }

    @Transactional
    public Trip addTrip(Long pathId, Long trainId, Boolean forward) throws SBBException {
        Path path = pathDAO.findById(pathId);
        Train train = trainDAO.findById(trainId);
        if (path.getStations().size() < 2) {
            throw new EmptyListException("You can't create trip with path which has less than 2 stations. Path:" + path);
        }
        Trip trip = new Trip(path, train, forward);
        tripDAO.create(trip);
        return trip;
    }

    private boolean checkLCI(Trip trip, Integer tripLCI) {
        if (!tripLCI.equals(trip.getLastChange())) {
            return false;
        } else {
            tripDAO.detach(trip);
            trip.incrementLastChange();
            return true;
        }
    }


    @Transactional
    public Trip editTrip(Long tripId, Long pathId, Long trainId, Boolean forward, Integer version)
            throws OutdateException, EntityNotFoundException, CascadeException {

        Trip trip = tripDAO.findById(tripId);

        Path path = pathDAO.findById(pathId);
        Train train = trainDAO.findById(trainId);
        List<Board> boardList = boardDAO.getBoardForTrip(trip);

        if (!boardList.isEmpty()) {
            if (!trip.getPath().equals(path) || !trip.isForward().equals(forward)) {
                throw new CascadeException("You can't edit route of trip which has board list");
            }

            int delta = train.getPlacesQty() - trip.getTrain().getPlacesQty();
            if (delta < 0 &&
                    passengerService.countFreePlaces(boardList.get(0), boardList.get(boardList.size() - 1)) < -delta) {
                throw new CascadeException("You can't edit train of trip because its quantity of places is less" +
                        " than count of tickets");
            }
        }

        trip.setPath(path);
        trip.setForward(forward);
        trip.setTrain(train);
        trip.setVersion(version);
        tripDAO.update(trip);
        return trip;
    }

    @Transactional
    public void removeTrip(Long tripId, Integer version) throws SBBException {
        Trip trip = tripDAO.findById(tripId);
        if (!boardDAO.getBoardForTrip(trip).isEmpty()) {
            throw new CascadeException("You can't delete trip which has board list");
        }
        tripDAO.delete(tripId, version);
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

