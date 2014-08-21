package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.*;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.dto.BoardDTO;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BoardService {
    BoardDAO boardDAO;
    TripDAO tripDAO;

    public BoardService(BoardDAO boardDAO, TripDAO tripDAO) {
        this.boardDAO = boardDAO;
        this.tripDAO = tripDAO;
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

    public List<BoardDTO> getBoardForStationForToday(Station station) {
        final long DAY = 1000L * 60 * 60 * 24;
        Timestamp after = new Timestamp(new Date().getTime());
        after.setHours(0);
        after.setMinutes(0);
        after.setSeconds(0);
        Timestamp before = new Timestamp(after.getTime() + DAY);
        List<BoardDTO> boardDTOList = new ArrayList<BoardDTO>();
        for (Board b : getBoardForStation(station, before, after)) {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setTripNumber(b.getTrip().getId());
            boardDTO.setPathTitle(b.getTrip().getPath().getTitle());
            boardDTO.setArriveTime(b.getArriveTime());
            boardDTO.setDepartureTime(b.getDepartureTime());
            boardDTO.setStandTime((int)(b.getArriveTime().getTime() - b.getArriveTime().getTime()));
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }
}
