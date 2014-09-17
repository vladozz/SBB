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
        DateTime dateTime = parseJSDate(date, stations.get(stations.size() - 1).getTimeZone());
        Timestamp timestamp = new Timestamp(dateTime.getMillis());
        for (Station station : stations) {
            Board boardLine = new Board(trip, station, timestamp, timestamp);
            boardDAO.create(boardLine);
            board.add(boardLine);
        }
        return board;
    }

    @Transactional
    public String changeBoard(BoardTripDTO[] boardTripDTOs) throws InvalidIdException {
        List<Board> boardList = new ArrayList<Board>();
        StringBuilder errorList = new StringBuilder();
        int errorCount = 0;
        for (int i = 0; i < boardTripDTOs.length; i++) {
            Board boardLine = prepareBoard(boardTripDTOs[i]);
            boardList.add(boardLine);
            if (boardLine.getArriveTime().compareTo(boardLine.getDepartureTime()) > 0) {
                errorCount++;
                String errorMessage = "Line " + boardLine.getId() + ": arrival is later than departure\n";
                errorList.append(errorMessage);
            }
            if (i != 0) {
                Board previousBoardLine = boardList.get(i - 1);
                if (boardLine.getArriveTime().compareTo(previousBoardLine.getDepartureTime()) < 0) {
                    errorCount++;
                    String errorMessage = "Line " + previousBoardLine.getId() +
                            ": departure is later than arrival at the Line " + boardLine.getId() + " \n";
                    errorList.append(errorMessage);
                }
            }
        }

        if (errorCount == 0) {
            for (Board b : boardList) {
                boardDAO.update(b);
            }
            return "";
        }
//        for (Board b : boardList) {
//            boardDAO.refresh(b);
//        }
        return errorList.toString();
    }

    private Board prepareBoard(BoardTripDTO boardTripDTO) throws InvalidIdException {
        Board board = boardDAO.findById(boardTripDTO.getBoardId());
        if (board == null) {
            throw new InvalidIdException("Board line doesn't exist. id: " + boardTripDTO.getBoardId());
        }
        boardDAO.detach(board);
        TimeZone timeZone = board.getStation().getTimeZone();
        board.setArriveTime(new Timestamp(
                parseJSDateTime(boardTripDTO.getArriveDate(), boardTripDTO.getArriveTime(), timeZone).getMillis()));
        board.setDepartureTime(new Timestamp(
                parseJSDateTime(boardTripDTO.getDepartureDate(), boardTripDTO.getDepartureTime(), timeZone).getMillis()));
        return board;
    }

    /**
     * Метод, возвращающий расписание для станции в интервале между after и before
     *
     * @param stationId station
     * @param before    before
     * @param after     after
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
        return new DateTime(date.get(0), date.get(1), date.get(2), 23, 59, DateTimeZone.forTimeZone(timeZone));
    }

    private static DateTime parseJSDateTime(String dateString, String timeString, TimeZone timeZone) {
        List<Integer> datetime = new ArrayList<Integer>(5);
        for (String s : dateString.split("-")) {
            datetime.add(Integer.parseInt(s));
        }
        for (String s : timeString.split(":")) {
            datetime.add(Integer.parseInt(s));
        }
        return new DateTime(datetime.get(0), datetime.get(1), datetime.get(2),
                datetime.get(3), datetime.get(4), DateTimeZone.forTimeZone(timeZone));
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
