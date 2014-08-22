package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.*;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.dto.BoardStationDTO;
import com.tsystems.javaschool.vm.dto.BoardTripDTO;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BoardService {
    private BoardDAO boardDAO;
    private TripDAO tripDAO;
    private PathDAO pathDAO;
    private TrainDAO trainDAO;

    public BoardService(BoardDAO boardDAO, TripDAO tripDAO, PathDAO pathDAO, TrainDAO trainDAO) {
        this.boardDAO = boardDAO;
        this.tripDAO = tripDAO;
        this.pathDAO = pathDAO;
        this.trainDAO = trainDAO;
    }

    public BoardService(BoardDAO boardDAO, TripDAO tripDAO) {
        this.boardDAO = boardDAO;
        this.tripDAO = tripDAO;
    }

    public Trip addTrip(Long pathID, Long trainID) {
        Path path = pathDAO.findById(pathID);
        Train train = trainDAO.findById(trainID);
        return addTrip(path, train);
    }
    public Trip addTrip(Path path, Train train) {
        Trip trip = new Trip(path, train);
        EntityTransaction trx = tripDAO.getTransaction();
        try {
            trx.begin();
            tripDAO.create(trip);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }
        return trip;
    }

    /**
     * Создание расписание для рейса
     * @param trip рейс
     * @param arrives список времен прибытия
     * @param departures список времен отправления
     * @return список созданных строчек расписания
     */
    public List<Board> generateBoardByTrip(Trip trip, List<Timestamp> arrives, List<Timestamp> departures) {
        List<Board> board = new ArrayList<Board>();
        List<Station> stations = trip.getPath().getStations();
        if (stations.size() != arrives.size() || stations.size() != departures.size()) {
            //TODO: throw MyException: different sizes of arrays
        }
        int n = stations.size();
        for (int i = 0; i < n; i++) {
            Board boardLine = new Board(trip, stations.get(i), arrives.get(i), departures.get(i));
            board.add(boardLine);
        }

        EntityTransaction trx = boardDAO.getTransaction();
        try {
            trx.begin();
            for (Board boardLine : board) {
                boardDAO.create(boardLine);
            }
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }
        return board;
    }

    /**
     * Метод, возвращающий расписание для станции в интервале между after и before
     * @param station
     * @param before
     * @param after
     * @return
     */
    public List<Board> getBoardForStation(Station station, Timestamp before, Timestamp after) {
        String queryString = "SELECT b FROM Board b WHERE b.station = :station and b.departureTime >= :after and " +
                "b.arriveTime <= :before";
        Query query = boardDAO.createQuery(queryString);
        query.setParameter("station", station);
        query.setParameter("before", before);
        query.setParameter("after", after);
        return query.getResultList();
    }

    public List<BoardStationDTO> getBoardForStationForToday(Station station) {
        final long DAY = 1000L * 60 * 60 * 24;
        Timestamp after = new Timestamp(new Date().getTime());
        after.setHours(0);
        after.setMinutes(0);
        after.setSeconds(0);
        Timestamp before = new Timestamp(after.getTime() + DAY);
        List<BoardStationDTO> boardStationDTOList = new ArrayList<BoardStationDTO>();
        for (Board b : getBoardForStation(station, before, after)) {
            BoardStationDTO boardStationDTO = new BoardStationDTO();
            boardStationDTO.setTripNumber(b.getTrip().getId());
            boardStationDTO.setPathTitle(b.getTrip().getPath().getTitle());
            boardStationDTO.setArriveTime(b.getArriveTime());
            boardStationDTO.setDepartureTime(b.getDepartureTime());
            boardStationDTO.setStandTime((int)(b.getDepartureTime().getTime() - b.getArriveTime().getTime()) / 60000);
            boardStationDTOList.add(boardStationDTO);
        }
        return boardStationDTOList;
    }

    public List<BoardTripDTO> getBoardForTrip(Long tripId) {
        Trip trip = tripDAO.findById(tripId);
        String queryString = "SELECT b FROM Board b WHERE b.trip = :trip";
        Query query = boardDAO.createQuery(queryString);
        query.setParameter("trip", trip);
        List<BoardTripDTO> boardTripDTOList = new ArrayList<>();
        for (Board b : (List<Board>) query.getResultList()) {
            BoardTripDTO boardTripDTO = new BoardTripDTO();
            boardTripDTO.setTripNumber(b.getTrip().getId());
            boardTripDTO.setStationTitle(b.getStation().getTitle());
            boardTripDTO.setArriveTime(b.getArriveTime());
            boardTripDTO.setDepartureTime(b.getDepartureTime());
            boardTripDTO.setStandTime((int)(b.getDepartureTime().getTime() - b.getArriveTime().getTime()) / 60000);
            boardTripDTOList.add(boardTripDTO);
        }
        return boardTripDTOList;
    }
}
