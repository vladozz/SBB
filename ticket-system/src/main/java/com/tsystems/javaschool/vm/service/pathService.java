package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;

import javax.persistence.EntityTransaction;
import java.util.List;

public class pathService {
    PathDAO pathDAO = new PathDAO();
    StationDAO stationDAO = new StationDAO();

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

    public void addStationToPath(Long pathId, Long stationId) throws Exception {
        addStationToPath(pathId, stationId, 0);
    }

    public void addStationToPath(Long pathId, Long stationId, int index) throws Exception {
        Path path = pathDAO.findById(pathId);
        Station station = stationDAO.findById(stationId);
        addStationToPath(path, station, index);
    }

    /**
     * Метод, добавляющий станцию в конец маршрута
     * @param path маршрут
     * @param station станция
     */
    public void addStationToPath(Path path, Station station) throws Exception {
        addStationToPath(path, station, 0);
    }

    /**
     * Метод, добавляющий станцию в маршрут (в середину или в конец)
     * @param path маршрут
     * @param station станция
     * @param index 0 - добавление в конец, от 1...n(кол-во станций в маршруте) - вставка на место 1го...n-го элемента
     *               со сдвигом всех последующих
     */
    public void addStationToPath(Path path, Station station, int index) throws Exception {
        EntityTransaction trx = pathDAO.getTransaction();
        try {
            trx.begin();
            List<Station> stations = path.getStations();
            if (index < 0 || index > stations.size()) {
                throw new Exception("Illegal index of station: " + "index = " + index
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
            }
        }
    }

    /**
     *
     * @param pathId
     * @param stationId
     * @param index от 1...n(кол-во станций в маршруте) - удаление 1го...n-го элемента
     *               со сдвигом всех последующих
     */
    public void removeStationFromPath(Long pathId, Long stationId, int index) throws Exception {
        Path path = pathDAO.findById(pathId);
        Station station = stationDAO.findById(stationId);
        removeStationFromPath(path, station, index);
    }

    /**
     *
     * @param path
     * @param station
     * @param index от 1...n(кол-во станций в маршруте) - удаление 1го...n-го элемента
     *               со сдвигом всех последующих
     */
    public void removeStationFromPath(Path path, Station station, int index) throws Exception {
        EntityTransaction trx = pathDAO.getTransaction();
        try {
            trx.begin();
            List<Station> stations = path.getStations();
            if (index <= 0 || index > stations.size()) {
                throw new Exception("Illegal index of station: " + "index = " + index
                        + "stations.size() = " + stations.size() + "path = " + path + "station = " + station);
                //TODO: possible change class of exception
            }
            stations.remove(index - 1);
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }
    }
}

