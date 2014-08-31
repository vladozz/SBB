package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.UserDAO;
import com.tsystems.javaschool.vm.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public UserService() {
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Map.Entry<Long, String> checkLoginAndCreateSession(String login, String password) {
        User user = userDAO.findByLogin(login);
        if (user != null) {
            String hash = Hasher.md5(password).toUpperCase();
            if (hash.equals(user.getPassword())) {
                return new AbstractMap.SimpleEntry<Long, String>(getRandomLong(), login);
            }
        } else {
            return null;
        }
        return null;
    }

    public Long getRandomLong() {
        return (long) (Math.random() * Long.MAX_VALUE);
    }
}
