package com.tsystems.javaschool.vm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "station")
public class Station implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "title")
    private String title;


    @Column(name = "time_zone")
    private String timeZone;



    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }



//    @Column(name = "time_zone")
//    private  TimeZone timeZone;

    @ManyToMany(mappedBy = "stations")
    private List<Path> paths;


    public Station() {}

    public Station(String title) {
        this.title = title;
    }

    public Station(String title, String timeZone) {
        this.title = title;
        this.timeZone = timeZone;
    }

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

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

//    public String getTimeZone() {
//        return timeZone.getID();
//    }
//
//    public void setTimeZone(String timeZone) {
//        this.timeZone = TimeZone.getTimeZone(timeZone);
//    }


    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", timeZone=" + timeZone +
                '}';
    }

}
