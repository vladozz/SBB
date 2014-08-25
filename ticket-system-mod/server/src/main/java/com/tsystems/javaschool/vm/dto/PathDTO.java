package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.util.List;

public class PathDTO implements Serializable {
    private static final long serialVersionUID = -2482485965854484693L;
    private Long id;
    private String title;
    private List<String> stations;

    public PathDTO() {
    }

    public PathDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public PathDTO(Long id, String title, List<String> stations) {
        this.id = id;
        this.title = title;
        this.stations = stations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    @Override
    public String toString() {
        return "PathDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", stations=" + stations +
                '}';
    }
}
