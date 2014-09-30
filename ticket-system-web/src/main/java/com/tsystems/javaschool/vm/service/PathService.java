package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.PathDAO;
import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.OutdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
    public Path addPath(Path path) {
        pathDAO.create(path);
        return path;
    }

    @Transactional
    public Path editPath(Path path) throws EntityNotFoundException, OutdateException {
        Path newPath = pathDAO.findById(path.getId());
        newPath.setTitle(path.getTitle());
        newPath.setReturnTitle(path.getReturnTitle());
        newPath.setVersion(path.getVersion());
        pathDAO.update(newPath);
        return pathDAO.findById(path.getId());
    }

    @Transactional
    public void removePath(Long pathId, Integer version) {
        pathDAO.delete(pathId, version);
    }

    @Transactional
    public Path findById(Long pathId) throws EntityNotFoundException {
        return pathDAO.findById(pathId);
    }

    @Transactional
    public Station addStationToPathSafe(Long pathId, Long stationId, Long stationBeforeInsertId, Integer version)
            throws OutdateException, EntityNotFoundException {

        Path path = pathDAO.findById(pathId);
        path.setVersion(version);
        Station station = stationDAO.findById(stationId);

        addStationToPathSafe(path, station, stationBeforeInsertId);
        return station;

    }

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

            path.getStations().addAll(newStations);
        }
        pathDAO.update(path);
        return path;
    }

    @Transactional
    public Path removeStationFromPathSafe(Long pathId, Long stationToRemoveId, Integer version)
            throws OutdateException, EntityNotFoundException {

        Path path = pathDAO.findById(pathId);
        path.setVersion(version);
        return removeStationFromPathSafe(path, stationToRemoveId);
    }

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

        //path = pathDAO.update(path);
        path.getStations().addAll(newStations);
        pathDAO.update(path);
        return path;
    }

    public List<Path> getAllPaths() {
        return pathDAO.findAll();
    }
    public List<Path> getAllRemovedPaths() {
        return pathDAO.findAllDeleted();
    }

    public List<Station> getStationsOfPath(Long pathId) throws EntityNotFoundException {
        return getStationsOfPath(pathId, true);
    }

    public List<Station> getStationsOfPath(Path path) {
        return getStationsOfPath(path, true);
    }

    public List<Station> getStationsOfPath(Long pathId, boolean forward) throws EntityNotFoundException {
        Path path = pathDAO.findById(pathId);
        return getStationsOfPath(path, forward);
    }

    public List<Station> getStationsOfPath(Path path, boolean forward) {
        List<Station> stations = path.getStations();
        if (!forward) {
            Collections.reverse(stations);
        }
        return stations;
    }
}

