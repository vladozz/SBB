package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.exception.InvalidIndexException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PathService {
    @Autowired
    private PathDAO pathDAO;
    @Autowired
    private StationDAO stationDAO;

    public PathService() {
    }

    @Transactional
    public void addPath(Path path) {
        pathDAO.create(path);
    }

    public Path addStationToPath(Long pathId, Long stationId) throws InvalidIndexException {
        return addStationToPath(pathId, stationId, 0);
    }

    @Transactional
    public Path addStationToPath(Long pathId, Long stationId, int index) throws InvalidIndexException {
        Path path = pathDAO.findById(pathId);
        Station station = stationDAO.findById(stationId);
        if (path != null && station != null) {
            return addStationToPath(path, station, index);
        } else {
            return null;
        }
    }

    /**
     * Метод, добавляющий станцию в маршрут (в середину или в конец)
     *
     * @param path    маршрут
     * @param station станция
     * @param index   0 - добавление в конец, от 1...n(кол-во станций в маршруте) - вставка на место 1го...n-го элемента
     *                со сдвигом всех последующих
     */
    private Path addStationToPath(Path path, Station station, int index) throws InvalidIndexException {
        List<Station> stations = path.getStations();
        if (stations == null) {
            stations = new ArrayList<Station>();
        }
        if (index < 0 || index > stations.size()) {
            throw new InvalidIndexException("Illegal index of station: " + "index = " + index
                    + "stations.size() = " + stations.size() + "path = " + path + "station = " + station);
        }

        if (index == 0) {
            stations.add(station);
        } else {
            stations.add(index - 1, station);
        }

        pathDAO.update(path);
        return path;
    }

    /**
     * @param pathId
     * @param index  от 1...n(кол-во станций в маршруте) - удаление 1го...n-го элемента
     *               со сдвигом всех последующих
     */
    @Transactional
    public Path removeStationFromPath(Long pathId, int index) throws InvalidIndexException {
        Path path = pathDAO.findById(pathId);
        if (path != null) {
            return removeStationFromPath(path, index);
        } else {
            return null;
        }

    }

    /**
     * @param path
     * @param index от 1...n(кол-во станций в маршруте) - удаление 1го...n-го элемента
     *              со сдвигом всех последующих
     */
    private Path removeStationFromPath(Path path, int index) throws InvalidIndexException {
        List<Station> stations = path.getStations();
        if (index <= 0 || index > stations.size()) {
            throw new InvalidIndexException("Illegal index of station: " + "index = " + index
                    + "stations.size() = " + stations.size() + "path = " + path);
        }
        stations.remove(index - 1);
        return path;
    }

    public List<Path> getAllPaths() {
        return pathDAO.findAll();
    }

    public List<Station> getStationsOfPath(Long pathId) {
        Path path = pathDAO.findById(pathId);
        if (path == null) {
            return null;
        } else {
            return path.getStations();
        }

    }
}

