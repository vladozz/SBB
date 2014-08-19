package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Passenger;
import com.tsystems.javaschool.vm.domain.Path;


import javax.persistence.Query;
import java.util.List;

public class PathDAO extends CommonDAO<Path> {
    public Path findByName(String title) {
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
}
