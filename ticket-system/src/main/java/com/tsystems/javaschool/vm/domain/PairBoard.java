package com.tsystems.javaschool.vm.domain;

public class PairBoard {
    Board departure;
    Board arrive;

    public PairBoard(Board departure, Board arrive) {
        this.departure = departure;
        this.arrive = arrive;
    }

    public Board getDeparture() {
        return departure;
    }

    public void setDeparture(Board departure) {
        this.departure = departure;
    }

    public Board getArrive() {
        return arrive;
    }

    public void setArrive(Board arrive) {
        this.arrive = arrive;
    }

    @Override
    public String toString() {
        return "PairBoard{" +
                "departure=" + departure +
                ", arrive=" + arrive +
                '}';
    }
}
