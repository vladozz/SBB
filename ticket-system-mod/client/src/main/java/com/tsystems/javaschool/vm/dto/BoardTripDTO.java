package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class BoardTripDTO implements Serializable {
    private static final long serialVersionUID = -4561874501129316650L;
    private Long boardId;
    private String stationTitle;
    private Timestamp arriveTime;
    private Timestamp departureTime;

    public BoardTripDTO() {
    }

    public BoardTripDTO(Long boardId, String stationTitle, Timestamp arriveTime, Timestamp departureTime) {
        this.boardId = boardId;
        this.stationTitle = stationTitle;
        this.arriveTime = arriveTime;
        this.departureTime = departureTime;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
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

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
                "boardId=" + boardId +
                ", stationTitle='" + stationTitle + '\'' +
                ", arriveTime=" + arriveTime +
                ", departureTime=" + departureTime +
                '}';
    }
}
