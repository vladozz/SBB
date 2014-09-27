package com.tsystems.javaschool.vm.converter;

import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.TripDTO;
import org.springframework.stereotype.Component;

@Component
public class TripConverter {
    public TripDTO convertToTripDTO(Trip trip) {
        System.out.println("trip = " + trip);
        TripDTO tripDTO = new TripDTO(trip.getId(), trip.isForward(),
                trip.getTrain().getId(), trip.getTrain().getNumber(),
                trip.getPath().getId(), trip.getPath().getTitle(trip.isForward()), trip.getLastChange());
        return tripDTO;
    }
}
