package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.TrainDAO;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainService {
    @Autowired
    TrainDAO trainDAO;

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
    public Train editTrain(Train train) throws EntityNotFoundException {
        trainDAO.update(train);
        return trainDAO.findById(train.getId());
    }


}
