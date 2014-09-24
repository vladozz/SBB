package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket extends SBBEntity implements Comparable<Ticket> {
    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @OneToOne
    @JoinColumn(name = "arrive")
    private Board arrive;

    @OneToOne
    @JoinColumn(name = "departure")
    private Board departure;

    public Ticket() {

    }

    public Ticket(Passenger passenger, Board departure, Board arrive) {
        this.departure = departure;
        this.passenger = passenger;
        this.arrive = arrive;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Board getArrive() {
        return arrive;
    }

    public void setArrive(Board arrive) {
        this.arrive = arrive;
    }

    public Board getDeparture() {
        return departure;
    }

    public void setDeparture(Board departure) {
        this.departure = departure;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", passenger=" + passenger +
                ", arrive=" + arrive +
                ", departure=" + departure +
                '}';
    }

    @Override
    public int compareTo(Ticket that) {
        int result = this.departure.getDepartureTime().compareTo(that.departure.getDepartureTime());
        if (result == 0) {
            result = this.arrive.getArriveTime().compareTo(that.arrive.getArriveTime());
            if (result == 0) {
                result = this.passenger.compareTo(that.passenger);
            }
        }
        return result;
    }
}

