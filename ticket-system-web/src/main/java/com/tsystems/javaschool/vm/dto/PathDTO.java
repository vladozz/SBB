package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;

public class PathDTO implements Serializable {
    private static final long serialVersionUID = -2482485965854484693L;
    private Long id;
    private String title;
    //    private List<String> stations;
    private String beginStation;
    private String endStation;
    private Integer lastChange;



    public PathDTO() {
    }

    public PathDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public PathDTO(Long id, String title, String beginStation, String endStation, Integer lastChange) {
        this.id = id;
        this.title = title;
        this.beginStation = beginStation;
        this.endStation = endStation;
        this.lastChange = lastChange;
    }

    public PathDTO(Long id, String title, String beginStation, String endStation) {
        this.id = id;
        this.title = title;
        this.beginStation = beginStation;
        this.endStation = endStation;
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

    public String getBeginStation() {
        return beginStation;
    }

    public void setBeginStation(String beginStation) {
        this.beginStation = beginStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public Integer getLastChange() {
        return lastChange;
    }

    public void setLastChange(Integer lastChange) {
        this.lastChange = lastChange;
    }
}
