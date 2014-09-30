package com.tsystems.javaschool.vm.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "board")
public class Board extends SBBEntity {


    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "arrive_time")
    @Type(type = "timestamp")
    private Timestamp arriveTime;

    @Column(name = "departure_time")
    @Type(type = "timestamp")
    private Timestamp departureTime;




    public Board() {
    }

    public Board(Trip trip, Station station, Timestamp arriveTime, Timestamp departureTime) {
        this.trip = trip;
        this.station = station;
        this.arriveTime = arriveTime;
        this.departureTime = departureTime;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Timestamp getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Timestamp arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", trip=" + trip +
                ", station=" + station +
                ", arriveTime=" + arriveTime +
                ", departureTime=" + departureTime +
                '}';
    }

}
