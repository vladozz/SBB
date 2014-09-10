package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.StationDAO;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.TimeZone;

@Service
public class StationService {
    @Autowired
    StationDAO stationDAO;

    @Transactional
    public void addStation(Station station) {
        stationDAO.create(station);
    }

    @Transactional
    public void editStation(Station station) {
        stationDAO.update(station);
    }

    @Transactional
    public void removeStation(Long stationId) {
        stationDAO.delete(stationId);
    }

    public List<Station> getAllStations() {
        return stationDAO.findAll();
    }

}
