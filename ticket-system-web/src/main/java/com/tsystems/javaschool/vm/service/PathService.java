package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.exception.InvalidIndexException;
import com.tsystems.javaschool.vm.exception.PathException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PathService {
    private static Logger logger = Logger.getLogger(PathService.class);
    @Autowired
    private PathDAO pathDAO;
    @Autowired
    private StationDAO stationDAO;

    public PathService() {
    }

    @Transactional
    public void addPath(Path path) {
        path.setLastChange(1);
        pathDAO.create(path);
    }

    @Transactional
    public boolean editPath(Path path) {
        Path newPath = pathDAO.findById(path.getId());
        if (checkLCI(newPath, path.getLastChange())) {
            newPath.setTitle(path.getTitle());
            pathDAO.update(newPath);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkLCI(Path path) {
        Path existingPath = pathDAO.findById(path.getId());
        if (existingPath == null || !path.getLastChange().equals(existingPath.getLastChange())) {
            return false;
        } else {
            path.incrementLastChange();
            return true;
        }
    }

    private boolean checkLCI(Path path, Integer pathLCI) {
        if (path == null || !pathLCI.equals(path.getLastChange())) {
            return false;
        } else {
            path.incrementLastChange();
            return true;
        }
    }

    @Transactional
    public void removePath(Long pathId) {
        pathDAO.delete(pathId);
    }

    @Transactional
    public Path findById(Long pathId) {
        return pathDAO.findById(pathId);
    }

    @Transactional
    public Path addStationToPath(Long pathId, Long stationId, int index, Integer lci) throws PathException {
        Path path = pathDAO.findById(pathId);
        Station station = stationDAO.findById(stationId);
        if (path != null && station != null) {
            if (checkLCI(path, lci)) {
                return addStationToPath(path, station, index);
            } else {
                return null;
            }
        } else {
            throw new PathException("Last change index were updated!");
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
        if (index < 0 || index > stations.size()) {
            throw new InvalidIndexException("Illegal index of station: " + "index = " + index
                    + "stations.size() = " + stations.size() + "path = " + path + "station = " + station);
        }
        System.out.println("index = " + index);
        System.out.println("path = " + path.getStations());
        if (index == 0) {
            stations.add(station);
        } else {
            List<Station> newStations = new ArrayList<Station>();
            newStations.addAll(stations);
            newStations.add(index - 1, station);

            while (!stations.isEmpty()) {
                stations.remove(stations.size() - 1);
            }
            //stations.removeAll(newStations);
            pathDAO.update(path);
            path.getStations().addAll(newStations);
        }
        System.out.println("path = " + path.getStations());
        pathDAO.update(path);
        System.out.println("path = " + pathDAO.findById(path.getId()).getStations());
        return path;
    }

    @Transactional
    public Path addStationToPathSafe(Long pathId, Long stationId, Long stationBeforeInsertId, Integer lci) throws PathException {
        Path path = pathDAO.findById(pathId);
        Station station = stationDAO.findById(stationId);
        if (path != null && station != null) {
            if (checkLCI(path, lci)) {
                return addStationToPathSafe(path, station, stationBeforeInsertId);
            } else {
                return null;
            }
        } else {
            throw new PathException("Last change index were updated!");
        }
    }

    /**
     * Метод, добавляющий станцию в маршрут (в середину или в конец)
     *
     * @param path    маршрут
     * @param station станция
     * @param stationBeforeInsertId
     */
    private Path addStationToPathSafe(Path path, Station station, Long stationBeforeInsertId) throws InvalidIndexException {
        List<Station> stations = path.getStations();
        if (stationBeforeInsertId == 0) {
            stations.add(station);
        } else {
            List<Station> newStations = new ArrayList<Station>();
            newStations.addAll(stations);

            for (int i = 0; i < newStations.size(); i++) {
                if (newStations.get(i).getId().equals(stationBeforeInsertId)) {
                    newStations.add(i, station);
                    break;
                }
            }

            while (!stations.isEmpty()) {
                stations.remove(stations.size() - 1);
            }
            //stations.removeAll(newStations);
            pathDAO.update(path);
            path.getStations().addAll(newStations);
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
    public Path removeStationFromPath(Long pathId, int index, Integer lci) throws PathException {
        Path path = pathDAO.findById(pathId);

        if (path != null) {
            if (checkLCI(path, lci)) {
                return removeStationFromPath(path, index);
            } else {
                throw new PathException("Last change index were updated!");
            }
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
        List<Station> newStations = new ArrayList<Station>();
        newStations.addAll(stations);
        newStations.remove(index - 1);

        while (!stations.isEmpty()) {
            stations.remove(stations.size() - 1);
        }

        pathDAO.update(path);
        path.getStations().addAll(newStations);

        return path;
    }

    /**
     * @param pathId
     * @param stationToRemoveId
     */
    @Transactional
    public Path removeStationFromPathSafe(Long pathId, Long stationToRemoveId, Integer lci) throws PathException {
        Path path = pathDAO.findById(pathId);

        if (path != null) {
            if (checkLCI(path, lci)) {
                return removeStationFromPathSafe(path, stationToRemoveId);
            } else {
                throw new PathException("Last change index were updated!");
            }
        } else {
            return null;
        }

    }

    /**
     * @param path
     * @param stationToRemoveId
     */
    private Path removeStationFromPathSafe(Path path, Long stationToRemoveId) throws InvalidIndexException {
        List<Station> stations = path.getStations();

        List<Station> newStations = new ArrayList<Station>();
        newStations.addAll(stations);

        for (int i = 0; i < newStations.size(); i++) {
            if (newStations.get(i).getId().equals(stationToRemoveId)) {
                newStations.remove(i);
                break;
            }
        }

        while (!stations.isEmpty()) {
            stations.remove(stations.size() - 1);
        }

        pathDAO.update(path);
        path.getStations().addAll(newStations);
        pathDAO.update(path);
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

