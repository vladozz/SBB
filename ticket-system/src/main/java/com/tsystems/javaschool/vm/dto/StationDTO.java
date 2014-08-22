package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.util.TimeZone;

public class StationDTO implements Serializable {
    private String title;
    private TimeZone timeZone;

    public StationDTO(String title, TimeZone timeZone) {
        this.title = title;
        this.timeZone = timeZone;
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
                "title='" + title + '\'' +
                ", timeZone=" + timeZone +
                '}';
    }
}
