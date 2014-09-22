package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;

public class DefTripDTO implements Serializable {

    private static final long serialVersionUID = 6433110031074206463L;
    Long arriveStationId;
    String arriveDate;
    String arriveTime;
    Long departureStationId;
    String departureDate;
    String departureTime;

    public DefTripDTO() {
    }

    public DefTripDTO(Long arriveStationId, String arriveDate, String arriveTime, Long departureStationId, String departureDate, String departureTime) {
        this.arriveStationId = arriveStationId;
        this.arriveDate = arriveDate;
        this.arriveTime = arriveTime;
        this.departureStationId = departureStationId;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
    }

    public Long getArriveStationId() {
        return arriveStationId;
    }

    public void setArriveStationId(Long arriveStationId) {
        this.arriveStationId = arriveStationId;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Long getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(Long departureStationId) {
        this.departureStationId = departureStationId;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "DefTripDTO{" +
                "arriveStationId=" + arriveStationId +
                ", arriveDate='" + arriveDate + '\'' +
                ", arriveTime='" + arriveTime + '\'' +
                ", departureStationId=" + departureStationId +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                '}';
    }
}
