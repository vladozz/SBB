package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StationService {
    @Autowired
    StationDAO stationDAO;

    @Transactional
    public Station addStation(Station station) {
        stationDAO.create(station);
        return station;
    }

    @Transactional
    public Station editStation(Station station) throws EntityNotFoundException {
        stationDAO.update(station);
        return stationDAO.findById(station.getId());
    }

    @Transactional
    public void removeStation(Long stationId, Integer version) {
        stationDAO.delete(stationId, version);
    }

    public List<Station> getAllStations() {
        return stationDAO.findAll();
    }

    public List<Station> getAllRemovedStations() {
        return stationDAO.findAllDeleted();
    }

}
