package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.User;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class UserDAO extends CommonDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    public List<User> findByLogin(String login) {
        String queryString = "SELECT u FROM User u WHERE LOWER(u.login) = :login";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("login", login.toLowerCase());
        return query.getResultList();
    }
}
