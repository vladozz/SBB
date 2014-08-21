package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "path")
public class Path extends SBBEntity {
    @Column(name = "title")
    private String title;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "path_station",
            joinColumns = {@JoinColumn(name = "path_id")},
            inverseJoinColumns = {@JoinColumn(name = "station_id")})
    private List<Station> stations;

    @OneToMany(mappedBy = "path")
    private List<Trip> trips;

    public Path() {
    }

    public Path(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    @Override
    public String toString() {
        return "Path{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
