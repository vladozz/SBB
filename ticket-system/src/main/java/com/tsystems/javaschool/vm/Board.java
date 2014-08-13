package com.tsystems.javaschool.vm;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "board")
public class Board {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "path_id")
    private Path path;

    @Column(name = "arrive_time")
    private Date arriveTime;

    @Column(name = "departure_time")
    private Date departureTime;

    public Board() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", train=" + train +
                ", station=" + station +
                ", path=" + path +
                ", arriveTime=" + arriveTime +
                ", departureTime=" + departureTime +
                '}';
    }
}
