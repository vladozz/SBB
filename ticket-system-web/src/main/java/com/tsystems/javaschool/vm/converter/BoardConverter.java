package com.tsystems.javaschool.vm.converter;

import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.PairBoard;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.BoardDTO;
import com.tsystems.javaschool.vm.dto.BoardStationDTO;
import com.tsystems.javaschool.vm.dto.BoardTripDTO;
import com.tsystems.javaschool.vm.dto.RespDefTripDTO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class BoardConverter {

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String timeFormat = "HH:mm";

    public BoardTripDTO convertToBoardTripDTO(Board board) {
        BoardTripDTO boardTripDTO = new BoardTripDTO();
        boardTripDTO.setBoardId(board.getId());
        boardTripDTO.setTripId(board.getTrip().getId());
        boardTripDTO.setStationTitle(board.getStation().getTitle());

        TimeZone timeZone = board.getStation().getTimeZone();
        boardTripDTO.setMinuteOffset(timeZone.getRawOffset() / 60000);

        DateTime arriveDateTime = new DateTime(board.getArriveTime().getTime(), DateTimeZone.forTimeZone(timeZone));
        boardTripDTO.setArriveDate(arriveDateTime.toString(dateFormat));
        boardTripDTO.setArriveTime(arriveDateTime.toString(timeFormat));

        DateTime departureDateTime = new DateTime(board.getDepartureTime().getTime(), DateTimeZone.forTimeZone(timeZone));
        boardTripDTO.setDepartureDate(departureDateTime.toString(dateFormat));
        boardTripDTO.setDepartureTime(departureDateTime.toString(timeFormat));

        boardTripDTO.setStandTime(Minutes.minutesBetween(arriveDateTime, departureDateTime).getMinutes());
        return boardTripDTO;
    }

    public BoardStationDTO convertToBoardStationDTO(Board board) {
        BoardStationDTO boardStationDTO = new BoardStationDTO();
        boardStationDTO.setTripId(board.getTrip().getId());
        boardStationDTO.setPathTitle(board.getTrip().getPath().getTitle());
        boardStationDTO.setTrainNumber(board.getTrip().getTrain().getNumber());
        TimeZone timeZone = board.getStation().getTimeZone();
        DateTime arriveDateTime = new DateTime(board.getArriveTime().getTime(), DateTimeZone.forTimeZone(timeZone));
        boardStationDTO.setArriveTime(arriveDateTime.toString(timeFormat));

        DateTime departureDateTime = new DateTime(board.getDepartureTime().getTime(), DateTimeZone.forTimeZone(timeZone));
        boardStationDTO.setDepartureTime(departureDateTime.toString(timeFormat));

        boardStationDTO.setStandTime(Minutes.minutesBetween(arriveDateTime, departureDateTime).getMinutes());
        return boardStationDTO;
    }

    public RespDefTripDTO convertToRespDefTripDTO(PairBoard pairBoard, int freePlaces) {
        RespDefTripDTO dto = new RespDefTripDTO();
        Board departure = pairBoard.getDeparture();
        Board arrive = pairBoard.getArrive();

        Trip trip = departure.getTrip();
        dto.setTripId(trip.getId());
        dto.setPathTitle(trip.getPath().getTitle());

        BoardDTO arriveDTO = dto.getArrive();
        arriveDTO.setBoardId(arrive.getId());
        DateTime arriveDateTime = new DateTime(arrive.getArriveTime(), 
                DateTimeZone.forTimeZone(arrive.getStation().getTimeZone()));
        arriveDTO.setDate(arriveDateTime.toString(dateFormat));
        arriveDTO.setTime(arriveDateTime.toString(timeFormat));

        BoardDTO departureDTO = dto.getDeparture();
        departureDTO.setBoardId(departure.getId());
        DateTime departureDateTime = new DateTime(departure.getDepartureTime(),
                DateTimeZone.forTimeZone(departure.getStation().getTimeZone()));
        departureDTO.setDate(departureDateTime.toString(dateFormat));
        departureDTO.setTime(departureDateTime.toString(timeFormat));

        int minutes = Minutes.minutesBetween(departureDateTime, arriveDateTime).getMinutes();
        dto.setRouteTime(String.format("%d:%02d", minutes / 60, minutes % 60));

        dto.setFreePlaces(freePlaces);

        return dto;

    }


}
