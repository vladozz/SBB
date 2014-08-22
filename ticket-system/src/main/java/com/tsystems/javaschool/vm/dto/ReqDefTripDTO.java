package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReqDefTripDTO implements Serializable {
    private Long tripId;
    private String trainNumber;
    private String pathTitle;
    private Long departureBoardId;
    private String departureStation;
    private Timestamp departureTime;
    private Long arriveBoardId;
    private String arriveStation;
    private Timestamp arriveTime;

    public ReqDefTripDTO() {
    }

    public ReqDefTripDTO(Long tripId, String trainNumber, String pathTitle,
                         Long departureBoardId, String departureStation, Timestamp departureTime,
                         Long arriveBoardId, String arriveStation, Timestamp arriveTime) {
        this.tripId = tripId;
        this.trainNumber = trainNumber;
        this.pathTitle = pathTitle;
        this.departureBoardId = departureBoardId;
        this.departureStation = departureStation;
        this.departureTime = departureTime;
        this.arriveBoardId = arriveBoardId;
        this.arriveStation = arriveStation;
        this.arriveTime = arriveTime;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getPathTitle() {
        return pathTitle;
    }

    public void setPathTitle(String pathTitle) {
        this.pathTitle = pathTitle;
    }

    public Long getDepartureBoardId() {
        return departureBoardId;
    }

    public void setDepartureBoardId(Long departureBoardId) {
        this.departureBoardId = departureBoardId;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Long getArriveBoardId() {
        return arriveBoardId;
    }

    public void setArriveBoardId(Long arriveBoardId) {
        this.arriveBoardId = arriveBoardId;
    }

    public String getArriveStation() {
        return arriveStation;
    }

    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }

    public Timestamp getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Timestamp arriveTime) {
        this.arriveTime = arriveTime;
    }

    @Override
    public String toString() {
        return "ReqDefTripDTO{" +
                "tripId=" + tripId +
                ", trainNumber='" + trainNumber + '\'' +
                ", pathTitle='" + pathTitle + '\'' +
                ", departureBoardId=" + departureBoardId +
                ", departureStation='" + departureStation + '\'' +
                ", departureTime=" + departureTime +
                ", arriveBoardId=" + arriveBoardId +
                ", arriveStation='" + arriveStation + '\'' +
                ", arriveTime=" + arriveTime +
                '}';
    }
}
