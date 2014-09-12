package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.BoardDAO;
import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.TrainDAO;
import com.tsystems.javaschool.vm.dao.TripDAO;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.exception.DifferentArrayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardDAO boardDAO;
    @Autowired
    private TripDAO tripDAO;
    @Autowired
    private PathDAO pathDAO;
    @Autowired
    private TrainDAO trainDAO;

    public BoardService() {
    }

    public Trip addTrip(Long pathID, Long trainID) {
        Path path = pathDAO.findById(pathID);
        Train train = trainDAO.findById(trainID);
        return addTrip(path, train);
    }

    public Trip addTrip(Path path, Train train) {
        if (path == null || train == null) {
            return null;
        }
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
     *
     * @param trip       рейс
     * @param arrives    список времен прибытия
     * @param departures список времен отправления
     * @return список созданных строчек расписания
     */
    public List<Board> generateBoardByTrip(Trip trip, List<Timestamp> arrives, List<Timestamp> departures) throws DifferentArrayException {
        if (trip == null) {
            return null;
        }
        List<Board> board = new ArrayList<Board>();
        List<Station> stations = trip.getPath().getStations();
        if (stations.size() != arrives.size() || stations.size() != departures.size()) {
            throw new DifferentArrayException("stations.size() = " + stations.size() +
                    " arrives.size() = " + arrives.size() + " departures.size() = " + departures.size());

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
     *
     * @param station
     * @param before
     * @param after
     * @return
     */
    public List<Board> getBoardForStation(Station station, Timestamp after, Timestamp before) {

        return boardDAO.getBoardForStation(station, after, before);
    }



    public List<Board> getBoardForTrip(Long tripId) {
        Trip trip = tripDAO.findById(tripId);
        if (trip == null) {
            return null;
        }
        String queryString = "SELECT b FROM Board b WHERE b.trip = :trip";
        Query query = boardDAO.createQuery(queryString);
        query.setParameter("trip", trip);
        return query.getResultList();
    }

    public List<PairBoard> getDefTrips(String departureStation, String arriveStation, Timestamp departureAfter, Timestamp arriveBefore) {
        String queryString = "SELECT b FROM Board b " +
                "WHERE b.station.title = :arriveStation and " +
                "b.arriveTime < :arriveBefore and b.arriveTime > :departureAfter";
        Query query = boardDAO.createQuery(queryString);
        query.setParameter("arriveStation", arriveStation);
        query.setParameter("departureAfter", departureAfter);
        query.setParameter("arriveBefore", arriveBefore);
        List<Board> boardsArrive = query.getResultList();
        queryString = "SELECT b FROM Board b " +
                "WHERE b.station.title = :departureStation" +
                " and b.departureTime > :departureAfter and b.departureTime < :arriveBefore ";
        query = boardDAO.createQuery(queryString);
        query.setParameter("departureStation", departureStation);
        query.setParameter("departureAfter", departureAfter);
        query.setParameter("arriveBefore", arriveBefore);
        List<Board> boardsDeparture = query.getResultList();
        List<PairBoard> pairBoards = new ArrayList<PairBoard>();
        for (Board ba : boardsArrive) {
            for (Board bd : boardsDeparture) {
                if (ba.getTrip().equals(bd.getTrip())) {
                    pairBoards.add(new PairBoard(bd, ba));
                    break;
                }
            }
        }
        return pairBoards;
    }
}
