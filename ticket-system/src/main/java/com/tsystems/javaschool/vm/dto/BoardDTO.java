package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class BoardDTO implements Serializable {
    private static final long serialVersionUID = -7055087988191590355L;
    private long tripNumber;
    private String pathTitle;
    private Timestamp arriveTime;
    private int standTime;
    private Timestamp departureTime;

    public BoardDTO() {
    }

    public BoardDTO(long tripNumber, String pathTitle, Timestamp arriveTime, int standTime, Timestamp departureTime) {
        this.tripNumber = tripNumber;
        this.pathTitle = pathTitle;
        this.arriveTime = arriveTime;
        this.standTime = standTime;
        this.departureTime = departureTime;
    }

    public long getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(long tripNumber) {
        this.tripNumber = tripNumber;
    }

    public String getPathTitle() {
        return pathTitle;
    }

    public void setPathTitle(String pathTitle) {
        this.pathTitle = pathTitle;
    }

    public Timestamp getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Timestamp arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getStandTime() {
        return standTime;
    }

    public void setStandTime(int standTime) {
        this.standTime = standTime;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }
}
