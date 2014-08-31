package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Path;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class PathDAO extends CommonDAO<Path> {

    public PathDAO() {
        super(Path.class);
    }

    public Path findByTitle(String title) {
        String queryString = "SELECT p FROM Path p WHERE LOWER(p.title) = :title";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("title", title.toLowerCase());
        List<Path> path = query.getResultList();
        if (path.isEmpty()) {
            return null;
        } else {
            return path.get(0);
        }
    }
}


