package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.PairBoard;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.Trip;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoardDAO extends CommonDAO<Board> {

    public BoardDAO() {
        super(Board.class);
    }

    public List<Board> getBoardForStation(Station station, Timestamp after, Timestamp before) {
        String queryString = "SELECT b FROM Board b WHERE b.station = :station and b.departureTime >= :after and " +
                "b.arriveTime <= :before";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("station", station);
        query.setParameter("before", before);
        query.setParameter("after", after);

        return query.getResultList();
    }

    public List<Board> getBoardForTrip(Trip trip) {
        String queryString = "SELECT b FROM Board b WHERE b.trip = :trip";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trip", trip);
        return query.getResultList();
    }

    public List<PairBoard> getBoard(Station departureStation, Station arriveStation, Timestamp departureAfter, Timestamp arriveBefore) {
        String queryString = "SELECT b FROM Board b " +
                "WHERE b.station = :arriveStation and " +
                "b.arriveTime <= :arriveBefore and b.arriveTime >= :departureAfter";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("arriveStation", arriveStation);
        query.setParameter("departureAfter", departureAfter);
        query.setParameter("arriveBefore", arriveBefore);
        List<Board> boardsArrive = query.getResultList();
        queryString = "SELECT b FROM Board b " +
                "WHERE b.station = :departureStation" +
                " and b.departureTime >= :departureAfter and b.departureTime <= :arriveBefore ";
        query = entityManager.createQuery(queryString);
        query.setParameter("departureStation", departureStation);
        query.setParameter("departureAfter", departureAfter);
        query.setParameter("arriveBefore", arriveBefore);
        List<Board> boardsDeparture = query.getResultList();
        List<PairBoard> pairBoards = new ArrayList<PairBoard>();
        for (Board ba : boardsArrive) {
            for (Board bd : boardsDeparture) {
                if (ba.getTrip().getId().equals(bd.getTrip().getId()) &&
                        ba.getArriveTime().compareTo(bd.getDepartureTime()) > 0) {

                    pairBoards.add(new PairBoard(bd, ba));
                    break;
                }
            }
        }
        return pairBoards;
    }
}
