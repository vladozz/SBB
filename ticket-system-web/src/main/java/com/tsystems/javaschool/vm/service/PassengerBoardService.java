package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.BoardDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.PairBoard;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.dto.DefTripDTO;
import com.tsystems.javaschool.vm.exception.*;
import com.tsystems.javaschool.vm.helper.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

@Service
public class PassengerBoardService {
    @Autowired
    private BoardDAO boardDAO;
    @Autowired
    private StationDAO stationDAO;
    @Autowired
    private DateHelper dateHelper;

    public PassengerBoardService() {
    }

    public List<Board> getBoardForStation(Long stationId, String date) throws EntityNotFoundException {
        Station station = stationDAO.findById(stationId);
        TimeZone timeZone = station.getTimeZone();
        Timestamp after = new Timestamp(dateHelper.parseBSDateTime(date, "00:00", timeZone).getMillis());
        Timestamp before = new Timestamp(dateHelper.parseBSDateTime(date, "23:59", timeZone).getMillis());
        return boardDAO.getBoardForStation(station, after, before);
    }

    public List<PairBoard> getDefTrips(DefTripDTO defTripDTO) throws EntityNotFoundException {

        Station departureStation = stationDAO.findById(defTripDTO.getDepartureStationId());
        Station arriveStation = stationDAO.findById(defTripDTO.getArriveStationId());

        Timestamp departureAfter = new Timestamp(dateHelper.parseBSDateTime(
                defTripDTO.getDepartureDate(), defTripDTO.getDepartureTime(), departureStation.getTimeZone())
                .getMillis());
        Timestamp arriveBefore = new Timestamp(dateHelper.parseBSDateTime(
                defTripDTO.getArriveDate(), defTripDTO.getArriveTime(), arriveStation.getTimeZone())
                .getMillis());

        return boardDAO.getBoard(departureStation, arriveStation, departureAfter, arriveBefore);
    }

    public List<PairBoard> getDefTrips(Long departureStationId, Long arriveStationId, Timestamp departureAfter, Timestamp arriveBefore) {

        return boardDAO.getBoard(departureStationId, arriveStationId, departureAfter, arriveBefore);
    }

}
