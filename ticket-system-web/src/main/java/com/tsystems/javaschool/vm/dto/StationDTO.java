package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.util.TimeZone;

public class StationDTO implements Serializable {

    private static final long serialVersionUID = -1752725050456199931L;
    private Long id;
    private String title;
    private TimeZone timeZone;

    public StationDTO(String title, TimeZone timeZone) {
        this.title = title;
        this.timeZone = timeZone;
    }

    public StationDTO(Long id, String title, TimeZone timeZone) {
        this.id = id;
        this.title = title;
        this.timeZone = timeZone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "StationDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", timeZone=" + timeZone +
                '}';
    }
}
