package com.tsystems.javaschool.vm.dao;


import com.tsystems.javaschool.vm.domain.Train;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;


@Component
public class TrainDAO extends CommonDAO<Train> {

    public TrainDAO() {
        super(Train.class);
    }

    public Train findByNumber(String number) {
        String queryString = "SELECT t FROM Train t WHERE LOWER(t.number) = :number";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("number", number.toLowerCase());
        List<Train> train = query.getResultList();
        if (train.isEmpty()) {
            return null;
        } else {
            return train.get(0);
        }
    }
}
