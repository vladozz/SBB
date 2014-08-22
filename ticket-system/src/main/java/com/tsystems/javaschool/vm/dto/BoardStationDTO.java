package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class BoardStationDTO implements Serializable {
    private static final long serialVersionUID = -7055087988191590355L;
    private long tripNumber;
    private String pathTitle;
    private String trainNumber;
    private Timestamp arriveTime;
    private int standTime;
    private Timestamp departureTime;

    public BoardStationDTO() {
    }

    public BoardStationDTO(long tripNumber, String pathTitle, String trainNumber, Timestamp arriveTime, int standTime, Timestamp departureTime) {
        this.tripNumber = tripNumber;
        this.pathTitle = pathTitle;
        this.trainNumber = trainNumber;
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

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
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
                ", pathTitle='" + pathTitle + '\'' +
                ", arriveTime=" + arriveTime +
                ", standTime=" + standTime +
                ", departureTime=" + departureTime +
                '}';
    }
}
