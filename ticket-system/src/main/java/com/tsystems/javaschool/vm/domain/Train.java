package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "train")
public class Train extends SBBEntity {
    @Id
    private Integer id;
    @Column(name = "places_qty")
    private short placesQty;

    @OneToMany(mappedBy = "train")
    List<Trip> trips;

    public Train() {

    }

    public Train(int id, short placesQty) {
        this.id = id;
        this.placesQty = placesQty;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public short getPlacesQty() {
        return placesQty;
    }

    public void setPlacesQty(short placesQty) {
        this.placesQty = placesQty;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", placesQty=" + placesQty +
                '}';
    }
}
