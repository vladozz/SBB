package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;

public class TicketDTO implements Serializable {
    private static final long serialVersionUID = -7555989551613177187L;

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
