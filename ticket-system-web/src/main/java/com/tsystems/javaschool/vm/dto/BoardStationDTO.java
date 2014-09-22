package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;

public class BoardStationDTO implements Serializable {
    private static final long serialVersionUID = -7055087988191590355L;
    private long tripId;
    private String pathTitle;
    private String trainNumber;
    private String arriveTime;
    private String departureTime;
    private int standTime;


    public BoardStationDTO() {
    }



    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
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

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getStandTime() {
        return standTime;
    }

    public void setStandTime(int standTime) {
        this.standTime = standTime;
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
                "tripId=" + tripId +
                ", pathTitle='" + pathTitle + '\'' +
                ", arriveTime=" + arriveTime +
                ", standTime=" + standTime +
                ", departureTime=" + departureTime +
                '}';
    }
}
