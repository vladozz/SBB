package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.BoardDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.dao.TripDAO;
import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.BoardTripDTO;
import com.tsystems.javaschool.vm.exception.*;
import com.tsystems.javaschool.vm.helper.DateHelper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private DateHelper dateHelper;

    public BoardService() {
    }

    @Transactional
    public List<Board> createEmptyBoard(Long tripId, String date, Integer lci) throws OutdateException, TripException, EmptyListException, EntityNotFoundException {
        Trip trip = tripDAO.findById(tripId);
        if (!checkLCI(trip, lci)) {
            throw new OutdateException("LCI in request: " + lci + "; LCI in database: " + trip.getLastChange());
        }
        if (!boardDAO.getBoardForTrip(trip).isEmpty()) {
            throw new TripException("Trip already has board!");
        }

        List<Station> stations = trip.getPath().getStations();
        if (stations.isEmpty()) {
            throw new EmptyListException("Can't create board for trip which path has less than 2 of stations." +
                    "Trip id: " + trip.getId() + "; path: " + trip.getPath());
        }

        List<Board> board = new ArrayList<Board>(stations.size());
        DateTime dateTime = dateHelper.parseBSDate(date, stations.get(stations.size() - 1).getTimeZone());
        Timestamp timestamp = new Timestamp(dateTime.getMillis());
        for (Station station : stations) {
            Board boardLine = new Board(trip, station, timestamp, timestamp);
            board.add(boardLine);
        }
        if (!trip.isForward()) {
            Collections.reverse(board);
        }
        for (Board boardLine : board) {
            boardDAO.create(boardLine);
        }
        return board;
    }

    @Transactional
    public List<String> changeBoard(BoardTripDTO[] boardTripDTOs) throws EntityNotFoundException, EmptyListException, TripException {
        validateDTOs(boardTripDTOs);

        List<Board> boardList = new ArrayList<Board>();
        List<String> errorList = new ArrayList<String>();
        int errorCount = 0;
        for (int i = 0; i < boardTripDTOs.length; i++) {
            Board boardLine = prepareBoard(boardTripDTOs[i]);
            boardList.add(boardLine);
            if (boardLine.getArriveTime().compareTo(boardLine.getDepartureTime()) > 0) {
                errorCount++;
                String errorMessage = "Line " + boardLine.getId() + ": arrival is later than departure\n";
                errorList.add(errorMessage);
            }
            if (i != 0) {
                Board previousBoardLine = boardList.get(i - 1);
                if (boardLine.getArriveTime().compareTo(previousBoardLine.getDepartureTime()) < 0) {
                    errorCount++;
                    String errorMessage = "Line " + previousBoardLine.getId() +
                            ": departure is later than arrival at the Line " + boardLine.getId() + " \n";
                    errorList.add(errorMessage);
                }
            }
        }

        if (errorCount == 0) {
            for (Board b : boardList) {
                boardDAO.update(b);
            }
        }
//        for (Board b : boardList) {
//            boardDAO.refresh(b);
//        }
        return errorList;
    }

    private void validateDTOs(BoardTripDTO[] boardTripDTOs) throws EmptyListException, EntityNotFoundException, TripException {
        if (boardTripDTOs.length == 0) {
            throw new EmptyListException("DTO array is empty");
        }
        List<Board> boards = getBoardForTrip(boardTripDTOs[0].getTripId());
        if (boards.size() != boardTripDTOs.length) {
            throw new TripException("DTO array size and board list size are different");
        }
        Set<Long> boardIds = new HashSet<Long>();
        for (Board b : boards) {
            boardIds.add(b.getId());
        }
        Set<Long> boardDTOIds = new HashSet<Long>();
        for (BoardTripDTO b : boardTripDTOs) {
            boardDTOIds.add(b.getBoardId());
        }
        if (!boardIds.equals(boardDTOIds)) {
            throw new TripException("DTO array is invalid");
        }
    }

    private Board prepareBoard(BoardTripDTO boardTripDTO) throws EntityNotFoundException {
        Board board = boardDAO.findById(boardTripDTO.getBoardId());
        boardDAO.detach(board);
        TimeZone timeZone = board.getStation().getTimeZone();
        board.setArriveTime(new Timestamp(
                dateHelper.parseBSDateTime(boardTripDTO.getArriveDate(), boardTripDTO.getArriveTime(), timeZone).getMillis()));
        board.setDepartureTime(new Timestamp(
                dateHelper.parseBSDateTime(boardTripDTO.getDepartureDate(), boardTripDTO.getDepartureTime(), timeZone).getMillis()));
        return board;
    }


    public List<Board> getBoardForStation(Long stationId, String date) throws EntityNotFoundException {
        Station station = stationDAO.findById(stationId);
        TimeZone timeZone = station.getTimeZone();
        Timestamp after = new Timestamp(dateHelper.parseBSDateTime(date, "00:00", timeZone).getMillis());
        Timestamp before = new Timestamp(dateHelper.parseBSDateTime(date, "23:59", timeZone).getMillis());
        return boardDAO.getBoardForStation(station, after, before);
    }

    public List<Board> getBoardForTrip(Long tripId) throws EntityNotFoundException {
        Trip trip = tripDAO.findById(tripId);
        return boardDAO.getBoardForTrip(trip);
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
