package com.tsystems.javaschool.vm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "station")
public class Station {
    @Id
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "time_zone")
    private byte timeZone;

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

    public byte getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(byte timeZone) {
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
