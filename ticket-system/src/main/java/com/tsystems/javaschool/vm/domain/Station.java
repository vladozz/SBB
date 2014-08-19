package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;
import java.util.List;
import java.util.TimeZone;

@Entity
@Table(name = "station")
public class Station extends SBBEntity {
    @Column(name = "title")
    private String title;


//    @Column(name = "time_zone")
//    private String timeZone;



//    public String getTimeZone() {
//        return timeZone;
//    }
//
//    public void setTimeZone(String timeZone) {
//        this.timeZone = timeZone;
//    }



    @Column(name = "time_zone")
    private TimeZone timeZone;

    @ManyToMany(mappedBy = "stations")
    private List<Path> paths;


    public Station() {}

    public Station(String title) {
        this.title = title;
    }

    public Station(String title, TimeZone timeZone) {
        this.title = title;
        this.timeZone = timeZone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }


    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", timeZone=" + timeZone +
                '}';
    }

}
