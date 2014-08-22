package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "train")
public class Train extends SBBEntity {
    @Column(name = "num")
    private String number;
    @Column(name = "places_qty")
    private Short placesQty;

    @OneToMany(mappedBy = "train")
    List<Trip> trips;

    public Train() {

    }

    public Train(String number, short placesQty) {
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

    public List<Trip> getTrips() {
        return trips;
    }

    @Override
    public String toString() {
        return "Train{" +
                "number='" + number + '\'' +
                ", placesQty=" + placesQty +
                '}';
    }
}
