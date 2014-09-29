package com.tsystems.javaschool.vm.dto;

public class TripDTO {

    private Long id;
    private Long pathId;
    private String pathTitle;
    private Boolean forward;
    private Long trainId;
    private String trainNumber;
    private Integer version;
    private String departureStation;
    private String arriveStation;

    public TripDTO() {


    }

    public TripDTO(Long id, Boolean forward, Long trainId, String trainNumber, Long pathId, String pathTitle, Integer version) {
        this.id = id;
        this.forward = forward;
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.pathId = pathId;
        this.pathTitle = pathTitle;
        this.version = version;
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

    public Boolean isForward() {
        return forward;
    }

    public void setForward(Boolean forward) {
        this.forward = forward;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    @Override
    public String toString() {
        return "TripDTO{" +
                "id=" + id +
                ", pathId=" + pathId +
                ", pathTitle='" + pathTitle + '\'' +
                ", forward=" + forward +
                ", trainId=" + trainId +
                ", trainNumber='" + trainNumber + '\'' +
                ", version=" + version +
                ", departureStation='" + departureStation + '\'' +
                ", arriveStation='" + arriveStation + '\'' +
                '}';
    }
}
