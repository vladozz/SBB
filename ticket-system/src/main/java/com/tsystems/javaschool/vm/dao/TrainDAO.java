package com.tsystems.javaschool.vm.dao;


import com.tsystems.javaschool.vm.domain.Train;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class TrainDAO extends CommonDAO<Train> {
    public TrainDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Train findByNumber(String number) {
        String queryString = "SELECT t FROM Train t WHERE LOWER(t.number) = :number";
        Query query = em.createQuery(queryString);
        query.setParameter("number", number.toLowerCase());
        List<Train> train = query.getResultList();
        if (train.size() == 0) {
            return null;
        } else {
            return train.get(0);
        }
    }
}
