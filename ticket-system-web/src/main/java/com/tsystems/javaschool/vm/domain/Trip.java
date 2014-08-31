package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trip")
public class Trip extends SBBEntity{
    @ManyToOne
    @JoinColumn(name = "path_id")
    private Path path;
    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @OneToMany(mappedBy = "trip")
    private List<Board> boardList;

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

    public List<Board> getBoardList() {
        return boardList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trip)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Trip trip = (Trip) o;

        return path.equals(trip.path) && train.equals(trip.train);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + path.hashCode();
        result = 31 * result + train.hashCode();
        return result;
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

