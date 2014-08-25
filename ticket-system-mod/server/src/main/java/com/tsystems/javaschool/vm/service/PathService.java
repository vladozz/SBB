package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.dao.TrainDAO;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.exception.InvalidIndexException;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class PathService {
    private PathDAO pathDAO;
    private StationDAO stationDAO;
    private TrainDAO trainDAO;

    public PathService(PathDAO pathDAO, StationDAO stationDAO, TrainDAO trainDAO) {
        this.pathDAO = pathDAO;
        this.stationDAO = stationDAO;
        this.trainDAO = trainDAO;
    }

    public Path addPath(String title) {
        Path path = new Path(title);
        EntityTransaction trx = pathDAO.getTransaction();
        try {
            trx.begin();
            pathDAO.create(path);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }
        return path;
    }

    public Station addStation(String title, TimeZone timeZone) {
        Station station = new Station(title, timeZone);
        EntityTransaction trx = stationDAO.getTransaction();
        try {
            trx.begin();
            stationDAO.create(station);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
                return null;
            }
        }
        return station;
    }

    public Train addTrain(String number, short placesQty) {
        Train train = new Train(number, placesQty);
        EntityTransaction trx = trainDAO.getTransaction();
        try {
            trx.begin();
            trainDAO.create(train);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }
        return train;
    }

    public Path addStationToPath(Long pathId, Long stationId)  throws InvalidIndexException {
        return addStationToPath(pathId, stationId, 0);
    }

    public Path addStationToPath(Long pathId, Long stationId, int index) throws InvalidIndexException {
        Path path = pathDAO.findById(pathId);
        Station station = stationDAO.findById(stationId);
        return addStationToPath(path, station, index);
    }

    public Path addStationToPath(String pathTitle, String stationTitle) throws InvalidIndexException {
        return addStationToPath(pathTitle, stationTitle, 0);
    }

    public Path addStationToPath(String pathTitle, String stationTitle, int index) throws InvalidIndexException {
        Path path = pathDAO.findByTitle(pathTitle);
        Station station = stationDAO.findByTitle(stationTitle);

        return addStationToPath(path, station, index);
    }

    /**
     * Метод, добавляющий станцию в конец маршрута
     * @param path маршрут
     * @param station станция
     */
    public Path addStationToPath(Path path, Station station)   throws InvalidIndexException {
        return addStationToPath(path, station, 0);
    }

    /**
     * Метод, добавляющий станцию в маршрут (в середину или в конец)
     * @param path маршрут
     * @param station станция
     * @param index 0 - добавление в конец, от 1...n(кол-во станций в маршруте) - вставка на место 1го...n-го элемента
     *               со сдвигом всех последующих
     */
    public Path addStationToPath(Path path, Station station, int index) throws InvalidIndexException {
        if (path == null || station == null) {
            return null;
        }
        EntityTransaction trx = pathDAO.getTransaction();
        try {
            trx.begin();
            List<Station> stations = path.getStations();
            if (stations == null) {
                stations = new ArrayList<Station>();
            }
            if (index < 0 || index > stations.size()) {
                throw new InvalidIndexException("Illegal index of station: " + "index = " + index
                        + "stations.size() = " + stations.size() + "path = " + path + "station = " + station);
                //TODO: possible change class of exception
            }

            if (index == 0) {
                stations.add(station);
            } else {
                stations.add(index - 1, station);
            }

            pathDAO.update(path);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
                return null;
            }
        }
        return path;
    }

    /**
     *
     * @param pathId
     * @param index от 1...n(кол-во станций в маршруте) - удаление 1го...n-го элемента
     *               со сдвигом всех последующих
     */
    public Path removeStationFromPath(Long pathId, int index) throws InvalidIndexException {
        Path path = pathDAO.findById(pathId);
        return removeStationFromPath(path, index);
    }

    public Path removeStationFromPath(String pathTitle, int index) throws InvalidIndexException {
        Path path = pathDAO.findByTitle(pathTitle);
        return removeStationFromPath(path, index);
    }

    /**
     *
     * @param path
     * @param index от 1...n(кол-во станций в маршруте) - удаление 1го...n-го элемента
     *               со сдвигом всех последующих
     */
    public Path removeStationFromPath(Path path, int index) throws InvalidIndexException {
        if (path == null) {
            return null;
        }
        EntityTransaction trx = pathDAO.getTransaction();
        try {
            trx.begin();
            List<Station> stations = path.getStations();
            if (index <= 0 || index > stations.size()) {
                throw new InvalidIndexException("Illegal index of station: " + "index = " + index
                        + "stations.size() = " + stations.size() + "path = " + path);
            }
            stations.remove(index - 1);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
                return null;
            }
        }
        return path;
    }

    public List<Train> getAllTrains() {
        return trainDAO.findAll();
    }

    public List<Path> getAllPaths() {
        return pathDAO.findAll();
    }

    public List<Station> getStationsOfPath(String pathTitle) {
        Path path = pathDAO.findByTitle(pathTitle);
        if (path == null) {
            return null;
        } else {
            return path.getStations();
        }

    }
}

