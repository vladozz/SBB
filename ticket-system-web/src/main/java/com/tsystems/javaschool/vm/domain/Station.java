package com.tsystems.javaschool.vm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.TimeZone;

@Entity
@Table(name = "station")
public class Station extends SBBEntity {
    @Column(name = "title")
    @NotNull(message = "Number cannot be empty")
    @Size(min = 1, max = 30,
            message = "The length of station title must be between 1 and 30 characters")
    private String title;

    @Column(name = "time_zone")
    private TimeZone timeZone;

    @ManyToMany(mappedBy = "stations")
    private List<Path> paths;


    public Station() {

    }

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
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Station that = ((Station) o);
        return this.title.compareToIgnoreCase(that.title);
    }
}
