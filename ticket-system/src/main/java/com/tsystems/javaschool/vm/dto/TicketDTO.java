package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class TicketDTO implements Serializable {
//    PassengerDTO passengerDTO;
//    Long tripId;
//    String departureStation;
//    String arriveStation;
//    String pathTitle;
//    String trainNumber;
//    Timestamp departureTime;
//    Timestamp arriveTime;


    public TicketDTO(Long ticketId) {
        this.ticketId = ticketId;
    }

    Long ticketId;
    //TODO:


    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
