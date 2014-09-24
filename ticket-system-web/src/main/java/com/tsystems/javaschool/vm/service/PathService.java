package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.PathException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PathService {
    private static final Logger logger = Logger.getLogger(PathService.class);
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
    public boolean editPath(Path path) throws EntityNotFoundException {
        Path newPath = pathDAO.findById(path.getId());
        if (checkLCI(newPath, path.getLastChange())) {
            newPath.setTitle(path.getTitle());
            pathDAO.update(newPath);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkLCI(Path path, Integer pathLCI) {
        if (!pathLCI.equals(path.getLastChange())) {
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
    public Path findById(Long pathId) throws EntityNotFoundException {
        return pathDAO.findById(pathId);
    }

    @Transactional
    public Path addStationToPathSafe(Long pathId, Long stationId, Long stationBeforeInsertId, Integer lci) throws PathException, EntityNotFoundException {
        Path path = pathDAO.findById(pathId);
        Station station = stationDAO.findById(stationId);

        if (checkLCI(path, lci)) {
            return addStationToPathSafe(path, station, stationBeforeInsertId);
        } else {
            throw new PathException("Last change index were updated!");
        }

    }

    /**
     * Метод, добавляющий станцию в маршрут (в середину или в конец)
     *
     * @param path                  маршрут
     * @param station               станция
     * @param stationBeforeInsertId
     */
    private Path addStationToPathSafe(Path path, Station station, Long stationBeforeInsertId) {
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
     * @param stationToRemoveId
     */
    @Transactional
    public Path removeStationFromPathSafe(Long pathId, Long stationToRemoveId, Integer lci) throws PathException, EntityNotFoundException {
        Path path = pathDAO.findById(pathId);

        if (checkLCI(path, lci)) {
            return removeStationFromPathSafe(path, stationToRemoveId);
        } else {
            throw new PathException("Last change index were updated!");
        }


    }

    /**
     * @param path
     * @param stationToRemoveId
     */
    private Path removeStationFromPathSafe(Path path, Long stationToRemoveId) {
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

    public List<Station> getStationsOfPath(Long pathId) throws EntityNotFoundException {
        Path path = pathDAO.findById(pathId);
        return path.getStations();
    }

    public List<Station> getStationsOfPath(Path path) {
        return path.getStations();
    }
}

