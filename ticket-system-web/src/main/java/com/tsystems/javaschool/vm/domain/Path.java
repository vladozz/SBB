package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "path")
public class Path extends SBBEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "return_title")
    private String returnTitle;

    @Column(name = "last_change")
    private Integer lastChange;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "path_station",
            joinColumns = {@JoinColumn(name = "path_id")},
            inverseJoinColumns = {@JoinColumn(name = "station_id")})
    private List<Station> stations;



    public Path() {
    }

    public Path(String title) {
        this.title = title;
    }

    public void incrementLastChange() {
        lastChange++;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle(boolean forward) {
        return forward ? title : returnTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReturnTitle() {
        return returnTitle;
    }

    public void setReturnTitle(String returnTitle) {
        this.returnTitle = returnTitle;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public Integer getLastChange() {
        return lastChange;
    }

    public void setLastChange(Integer lastChange) {
        this.lastChange = lastChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Path)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Path path = (Path) o;

        if (!title.equals(path.title)) {
            return false;
        }

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
