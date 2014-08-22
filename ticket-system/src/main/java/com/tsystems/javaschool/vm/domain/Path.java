package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;
import java.util.ArrayList;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;
        if (!super.equals(o)) return false;

        Path path = (Path) o;

        if (!title.equals(path.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Path{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
