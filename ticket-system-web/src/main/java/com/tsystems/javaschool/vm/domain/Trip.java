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
    @Column(name = "last_change")
    private Integer lastChange;
    @Column(name = "forward")
    private Boolean forward;

    @OneToMany(mappedBy = "trip")
    private List<Board> boardList;

    public Trip() {

    }

    public Trip(Path path, Train train, Boolean forward) {
        this.path = path;
        this.train = train;
        this.forward = forward;
        lastChange = 1;
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

    public Integer getLastChange() {
        return lastChange;
    }

    public void setLastChange(Integer lastChange) {
        this.lastChange = lastChange;
    }

    public Boolean isForward() {
        return forward;
    }

    public void setForward(Boolean forward) {
        this.forward = forward;
    }

    public List<Board> getBoardList() {
        return boardList;
    }

    public void setBoardList(List<Board> boardList) {
        this.boardList = boardList;
    }

    public void incrementLastChange() {
        lastChange++;
    }


    @Override
    public String toString() {
        return "Trip{" +
                "path=" + path +
                ", train=" + train +
                ", lastChange=" + lastChange +
                ", forward=" + forward;
    }
}

