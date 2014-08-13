package com.tsystems.javaschool.vm;

import javax.persistence.*;
import java.util.List;
import java.util.TimeZone;

@Entity
@Table(name = "station")
public class Station {
    @Id
    private int id;
    @Column(name = "title")
    private String title;
//    @Column(name = "time_zone")
//    private String timeZone;
    @Column(name = "time_zone")
    private TimeZone timeZone;

    @ManyToMany(mappedBy = "stations")
    private List<Path> paths;


    public Station() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public String getTimeZone() {
//        return timeZone;
//    }
//
//    public void setTimeZone(String timeZone) {
//        this.timeZone = timeZone;
//    }

    public String getTimeZone() {
        return timeZone.getID();
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = TimeZone.getTimeZone(timeZone);
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
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
