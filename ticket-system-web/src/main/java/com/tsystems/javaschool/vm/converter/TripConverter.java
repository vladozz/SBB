package com.tsystems.javaschool.vm.converter;

import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.TripDTO;
import org.springframework.stereotype.Component;

@Component
public class TripConverter {
    public TripDTO convertToTripDTO(Trip trip) {
        TripDTO tripDTO = new TripDTO(trip.getId(), trip.getForward(),
                trip.getTrain().getId(), trip.getTrain().getNumber(),
                trip.getPath().getId(), trip.getPath().getTitle(trip.getForward()), trip.getLastChange());
        return tripDTO;
    }
}
