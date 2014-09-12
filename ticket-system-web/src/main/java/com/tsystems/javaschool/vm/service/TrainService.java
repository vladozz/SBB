package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.TrainDAO;
import com.tsystems.javaschool.vm.domain.Train;
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
    public void removeTrain(Long trainId) {
        trainDAO.delete(trainId);
    }


    public List<Train> getAllTrains() {
        return trainDAO.findAll();
    }

    @Transactional
    public void editTrain(Train train) {
        Train exTrain = trainDAO.findById(train.getId());
        exTrain.setNumber(train.getNumber());
        exTrain.setPlacesQty(train.getPlacesQty());
        trainDAO.update(exTrain);
    }
}
