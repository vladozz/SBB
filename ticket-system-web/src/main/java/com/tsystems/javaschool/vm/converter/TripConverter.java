package com.tsystems.javaschool.vm.converter;

import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.TripDTO;
import com.tsystems.javaschool.vm.helper.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TripConverter {
    @Autowired
    private DateHelper dateHelper;

    public TripDTO convertToTripDTO(Trip trip, List<Board> boardList) {
        TripDTO tripDTO = convertToTripDTO(trip);

        if (!boardList.isEmpty()) {
            tripDTO.setDepartureDate(dateHelper.departureDateToString(boardList.get(0)));
            tripDTO.setArriveDate(dateHelper.arriveDateToString(boardList.get(boardList.size() - 1)));
        }
        return tripDTO;
    }

    public TripDTO convertToTripDTO(Trip trip) {
        TripDTO tripDTO = new TripDTO(trip.getId(), trip.getForward(),
                trip.getTrain().getId(), trip.getTrain().getNumber(),
                trip.getPath().getId(), trip.getPath().getTitle(trip.getForward()), trip.getLastChange());

        return tripDTO;
    }
}
