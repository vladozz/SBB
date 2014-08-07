package com.tsystems.javaschool.vm;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "board")
public class Board {
    @Id
    private int id;
    @Column(name = "arrive_time")
    private Date arriveTime;
    @Column(name = "departure_time")
    private Date departureTime;
    @OneToOne
    @JoinColumn (name = "train_id")
    private Train train;
    @OneToOne
    @JoinColumn (name = "station_id")
    private Station station;

    public Board() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", arriveTime=" + arriveTime +
                ", departureTime=" + departureTime +
                ", train=" + train +
                ", station=" + station +
                '}';
    }
}
