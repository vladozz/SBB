package com.tsystems.javaschool.vm;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn (name = "passenger_id")
    private Passenger passenger;

    @OneToOne
    @JoinColumn(name = "arrive")
    private Board arrive;

    @OneToOne
    @JoinColumn (name = "departure")
    private Board departure;

    public Ticket() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

