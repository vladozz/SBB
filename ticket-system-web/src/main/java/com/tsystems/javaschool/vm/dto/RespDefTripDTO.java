package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;

public class RespDefTripDTO implements Serializable {

    private static final long serialVersionUID = -5826721360633584105L;
    private Long tripId;
    private String pathTitle;
    private BoardDTO departure;
    private BoardDTO arrive;
    private String routeTime;
    private int freePlaces;

    public RespDefTripDTO() {
        departure = new BoardDTO();
        arrive = new BoardDTO();
    }



    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getPathTitle() {
        return pathTitle;
    }

    public void setPathTitle(String pathTitle) {
        this.pathTitle = pathTitle;
    }

    public BoardDTO getDeparture() {
        return departure;
    }

    public void setDeparture(BoardDTO departure) {
        this.departure = departure;
    }

    public BoardDTO getArrive() {
        return arrive;
    }

    public void setArrive(BoardDTO arrive) {
        this.arrive = arrive;
    }

    public String getRouteTime() {
        return routeTime;
    }

    public void setRouteTime(String routeTime) {
        this.routeTime = routeTime;
    }

    public Integer getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(Integer freePlaces) {
        this.freePlaces = freePlaces;
    }
}
