package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class UserDAO extends CommonDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    public User findByLogin(String login) throws EntityNotFoundException {
        List<User> users = getUsersByLogin(login);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("User", login);
        }
        return users.get(0);
    }

    public boolean ifExists(String login)  {
        List<User> users = getUsersByLogin(login);
        return !users.isEmpty();
    }

    private List<User> getUsersByLogin(String login) {
        String queryString = "SELECT u FROM User u WHERE LOWER(u.login) = :login";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("login", login.toLowerCase());
        return query.getResultList();
    }
}
