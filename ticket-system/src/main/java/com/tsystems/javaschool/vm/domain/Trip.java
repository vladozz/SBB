package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;

@Entity
@Table(name = "trip")
public class Trip extends SBBEntity{
    @ManyToOne
    @JoinColumn(name = "path_id")
    private Path path;
    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    public Trip() {
    }

    public Trip(Path path, Train train) {
        this.path = path;
        this.train = train;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", path=" + path +
                ", train=" + train +
                '}';
    }
}

