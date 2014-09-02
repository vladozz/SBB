package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;

public class TrainDTO implements Serializable {

    private static final long serialVersionUID = 3326713079683884452L;
    private Long id;
    private String number;
    private String placesQty;

    public TrainDTO() {
    }

    public TrainDTO(String number, String placesQty) {

        this.number = number;
        this.placesQty = placesQty;
    }

    public TrainDTO(Long id, String number, String placesQty) {

        this.id = id;
        this.number = number;
        this.placesQty = placesQty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPlacesQty() {
        return placesQty;
    }

    public void setPlacesQty(String placesQty) {
        this.placesQty = placesQty;
    }

    @Override
    public String toString() {
        return "TrainDTO{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", placesQty=" + placesQty +
                '}';
    }
}

