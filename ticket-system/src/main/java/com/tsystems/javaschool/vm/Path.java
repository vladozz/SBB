package com.tsystems.javaschool.vm;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "path")
public class Path {
    @Id
    private int id;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "path_station",
            joinColumns = {@JoinColumn(name = "path_id")},
            inverseJoinColumns = {@JoinColumn(name = "station_id")})
    private List<Station> stations;

    public Path() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @Override
    public String toString() {
        return "Path{" +
                "id=" + id +
                ", stations=" + stations +
                '}';
    }
}
