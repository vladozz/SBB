package com.tsystems.javaschool.vm.dto;

public class TripDTO {

    private Long id;
    private Long pathId;
    private String pathTitle;
    private Long trainId;
    private String trainNumber;
    private Integer lastChange;
    private String departureStation;
    private String arriveStation;

    public TripDTO() {


    }

    public TripDTO(Long id, Long trainId, String trainNumber, Long pathId, String pathTitle, Integer lastChange) {
        this.id = id;
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.pathId = pathId;
        this.pathTitle = pathTitle;
        this.lastChange = lastChange;
        this.departureStation = "";
        this.arriveStation = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public String getPathTitle() {
        return pathTitle;
    }

    public void setPathTitle(String pathTitle) {
        this.pathTitle = pathTitle;
    }

    public Integer getLastChange() {
        return lastChange;
    }

    public void setLastChange(Integer lastChange) {
        this.lastChange = lastChange;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArriveStation() {
        return arriveStation;
    }

    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }
}
