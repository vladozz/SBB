package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.BoardDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.dao.TripDAO;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.dto.BoardTripDTO;
import com.tsystems.javaschool.vm.exception.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.*;

@Service
public class BoardService {
    @Autowired
    private BoardDAO boardDAO;
    @Autowired
    private StationDAO stationDAO;
    @Autowired
    private TripDAO tripDAO;

    public BoardService() {
    }

    @Transactional
    public List<Board> createEmptyBoard(Long tripId, String date, Integer lci) throws SBBException {
        Trip trip = tripDAO.findById(tripId);
        if (trip == null) {
            throw new InvalidIdException("Trip doesn't exist. id: " + tripId);
        }
        if (!checkLCI(trip, lci)) {
            throw new OutdateException("LCI in request: " + lci + "; LCI in database: " + trip.getLastChange());
        }
        if (!trip.getBoardList().isEmpty()) {
            throw new TripException("Trip already has board!");
        }

        List<Station> stations = trip.getPath().getStations();
        if (stations.isEmpty()) {
            throw new EmptyListException("Can't create board for trip which path has empty list of stations." +
                    "Trip id: " + trip.getId() + "; path: " + trip.getPath());
        }

        List<Board> board = new ArrayList<Board>(stations.size());
        DateTime dateTime = parseJSDate(date, stations.get(0).getTimeZone());
        Timestamp timestamp = new Timestamp(dateTime.getMillis());
        for (Station station : stations) {
            Board boardLine = new Board(trip, station, timestamp, timestamp);
            boardDAO.create(boardLine);
            board.add(boardLine);
        }
        return board;
    }

    /**
     * Метод, возвращающий расписание для станции в интервале между after и before
     *
     * @param stationId station
     * @param before  before
     * @param after   after
     * @return return
     */
    public List<Board> getBoardForStation(Long stationId, Timestamp after, Timestamp before) throws InvalidIdException {
        Station station = stationDAO.findById(stationId);
        if (station == null) {
            throw new InvalidIdException("Station doesn't exist. id: " + stationId);
        }
        return boardDAO.getBoardForStation(station, after, before);
    }

    public List<Board> getBoardForTrip(Long tripId) throws InvalidIdException {
        Trip trip = tripDAO.findById(tripId);
        if (trip == null) {
            throw new InvalidIdException("Trip doesn't exist. id: " + tripId);
        }
        return boardDAO.getBoardForTrip(trip);
    }

    public List<PairBoard> getDefTrips(Long departureStationId, Long arriveStationId, Timestamp departureAfter, Timestamp arriveBefore) {

        return boardDAO.getBoard(departureStationId, arriveStationId, departureAfter, arriveBefore);
    }

    private static DateTime parseJSDate(String dateString, TimeZone timeZone) {
        List<Integer> date = new ArrayList<Integer>(3);
        for (String s : dateString.split("-")) {
            date.add(Integer.parseInt(s));
        }
        return new DateTime(date.get(0), date.get(1), date.get(2), 0, 0, DateTimeZone.forTimeZone(timeZone));
    }

    private boolean checkLCI(Trip trip, Integer tripLCI) {
        if (!tripLCI.equals(trip.getLastChange())) {
            return false;
        } else {
            trip.incrementLastChange();
            return true;
        }
    }


}
