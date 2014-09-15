package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class BoardTripDTO implements Serializable {
    private static final long serialVersionUID = -4561874501129316650L;
    private Long boardId;
    private Long tripId;
    private String stationTitle;
    private String departureDate;
    private String departureTime;
    private String arriveDate;
    private String arriveTime;
    private Integer standTime;

    public BoardTripDTO() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getStationTitle() {
        return stationTitle;
    }

    public void setStationTitle(String stationTitle) {
        this.stationTitle = stationTitle;
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

    public Integer getStandTime() {
        return standTime;
    }

    public void setStandTime(Integer standTime) {
        this.standTime = standTime;
    }
}
