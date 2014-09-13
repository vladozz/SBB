package com.tsystems.javaschool.vm.dto;

public class TripDTO {

    private Long id;
    private Long trainId;
    private String trainNumber;
    private Long pathId;
    private String pathTitle;


    public TripDTO() {
    }

    public TripDTO(Long id, Long trainId, String trainNumber, Long pathId, String pathTitle) {
        this.id = id;
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.pathId = pathId;
        this.pathTitle = pathTitle;
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
}
