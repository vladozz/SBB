package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class BoardTripDTO implements Serializable {
    private static final long serialVersionUID = -4561874501129316650L;
    private long tripNumber;
    private String stationTitle;
    private Timestamp arriveTime;
    private int standTime;
    private Timestamp departureTime;

    public BoardTripDTO() {
    }

    public BoardTripDTO(long tripNumber, String stationTitle, Timestamp arriveTime, int standTime, Timestamp departureTime) {
        this.tripNumber = tripNumber;
        this.stationTitle = stationTitle;
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

    public String getStationTitle() {
        return stationTitle;
    }

    public void setStationTitle(String stationTitle) {
        this.stationTitle = stationTitle;
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

    @Override
    public String toString() {
        return "BoardDTO{" +
                "tripNumber=" + tripNumber +
                ", stationTitle='" + stationTitle + '\'' +
                ", arriveTime=" + arriveTime +
                ", standTime=" + standTime +
                ", departureTime=" + departureTime +
                '}';
    }
}
