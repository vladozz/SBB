package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.TrainDAO;
import com.tsystems.javaschool.vm.dao.TripDAO;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.exception.CascadeException;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainService {
    @Autowired
    private TrainDAO trainDAO;
    @Autowired
    private TripDAO tripDAO;

    @Transactional
    public Train addTrain(Train train) {
        trainDAO.create(train);
        return train;
    }

    @Transactional
    public void removeTrain(Long trainId, Integer version) {
        trainDAO.delete(trainId, version);
    }


    public List<Train> getAllTrains() {
        return trainDAO.findAll();
    }

    public List<Train> getAllRemovedTrains() {
        return trainDAO.findAllDeleted();
    }

    @Transactional
    public Train editTrain(Train train) throws EntityNotFoundException, CascadeException {
        if (!tripDAO.filterTripsByTrain(train.getId()).isEmpty()) {
            throw new CascadeException("You cannot edit train which have trips");
        }
        trainDAO.update(train);
        return trainDAO.findById(train.getId());
    }


}
