package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DefTripDTO implements Serializable {

    private static final long serialVersionUID = 6433110031074206463L;
    String arriveStation;
    String departureStation;
    Timestamp departureTime;
    Timestamp arriveTime;

    public DefTripDTO() {
    }

    public DefTripDTO(String arriveStation, String departureStation, Timestamp departureTime, Timestamp arriveTime) {
        this.arriveStation = arriveStation;
        this.departureStation = departureStation;
        this.departureTime = departureTime;
        this.arriveTime = arriveTime;
    }

    public String getArriveStation() {
        return arriveStation;
    }

    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
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

    public Timestamp getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Timestamp arriveTime) {
        this.arriveTime = arriveTime;
    }

    @Override
    public String toString() {
        return "DefTripDTO{" +
                "arriveStation='" + arriveStation + '\'' +
                ", departureStation='" + departureStation + '\'' +
                ", departureTime=" + departureTime +
                ", arriveTime=" + arriveTime +
                '}';
    }
}
