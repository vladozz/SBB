package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserDAO extends CommonDAO<User> {

    public UserDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public User findByLogin(String login) {
        String queryString = "SELECT u FROM User u WHERE LOWER(u.login) = :login";
        Query query = em.createQuery(queryString);
        query.setParameter("login", login.toLowerCase());
        List<User> list = query.getResultList();
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
