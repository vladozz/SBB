package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.Station;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.sql.Timestamp;
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
}
