package com.tsystems.javaschool.vm.dto;

import com.tsystems.javaschool.vm.domain.Passenger;

public class BuyTicketDTO {
    PassengerDTO passengerDTO;
    Long departureBoardId;
    Long arriveBoardId;

    public BuyTicketDTO() {
    }

    public BuyTicketDTO(PassengerDTO passengerDTO, Long departureBoardId, Long arriveBoardId) {
        this.passengerDTO = passengerDTO;
        this.departureBoardId = departureBoardId;
        this.arriveBoardId = arriveBoardId;
    }

    public PassengerDTO getPassengerDTO() {
        return passengerDTO;
    }

    public void setPassengerDTO(PassengerDTO passengerDTO) {
        this.passengerDTO = passengerDTO;
    }

    public Long getDepartureBoardId() {
        return departureBoardId;
    }

    public void setDepartureBoardId(Long departureBoardId) {
        this.departureBoardId = departureBoardId;
    }

    public Long getArriveBoardId() {
        return arriveBoardId;
    }

    public void setArriveBoardId(Long arriveBoardId) {
        this.arriveBoardId = arriveBoardId;
    }

    @Override
    public String toString() {
        return "BuyTicketDTO{" +
                "passengerDTO=" + passengerDTO +
                ", departureBoardId=" + departureBoardId +
                ", arriveBoardId=" + arriveBoardId +
                '}';
    }
}
