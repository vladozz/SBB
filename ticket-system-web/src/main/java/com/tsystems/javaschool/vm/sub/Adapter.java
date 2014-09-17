package com.tsystems.javaschool.vm.sub;

import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.dto.BoardTripDTO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;

import java.util.TimeZone;

public class Adapter {
    public static BoardTripDTO convertToBoardTripDTO(Board board) {
        String dateFormat = "yyyy-MM-dd";
        String timeFormat = "HH:mm";
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
}
