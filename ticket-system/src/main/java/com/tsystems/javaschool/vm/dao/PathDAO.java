package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Passenger;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;


import javax.persistence.Query;
import java.util.List;

public class PathDAO extends CommonDAO<Path> {
    public Path findByTitle(String title) {
        String queryString = "SELECT p FROM Path p WHERE LOWER(p.title) = :title";
        Query query = em.createQuery(queryString);
        query.setParameter("title", title.toLowerCase());
        List<Path> path = query.getResultList();
        if (path.size() == 0) {
            return null;
        } else {
            return path.get(0);
        }
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
    }

    /**
     *
     * @param path
     * @param station
     * @param index от 1...n(кол-во станций в маршруте) - удаление 1го...n-го элемента
     *               со сдвигом всех последующих
     */
    public void removeStationFromPath(Path path, Station station, int index) throws Exception {
        List<Station> stations = path.getStations();
        if (index <= 0 || index > stations.size()) {
            throw new Exception("Illegal index of station: " + "index = " + index
                    + "stations.size() = " + stations.size() + "path = " + path + "station = " + station);
            //TODO: possible change class of exception
        }
        stations.remove(index - 1);
    }
}
