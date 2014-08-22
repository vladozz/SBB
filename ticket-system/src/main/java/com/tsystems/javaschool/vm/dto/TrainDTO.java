package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.util.TimeZone;

public class TrainDTO implements Serializable {
    private String number;
    private Short placesQty;

    public TrainDTO(String number, Short placesQty) {
        this.number = number;
        this.placesQty = placesQty;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Short getPlacesQty() {
        return placesQty;
    }

    public void setPlacesQty(Short placesQty) {
        this.placesQty = placesQty;
    }

    @Override
    public String toString() {
        return "TrainDTO{" +
                "number='" + number + '\'' +
                ", placesQty=" + placesQty +
                '}';
    }
}

