package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.RoleDAO;
import com.tsystems.javaschool.vm.dao.UserDAO;
import com.tsystems.javaschool.vm.domain.Role;
import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.exception.InvalidIdException;
import com.tsystems.javaschool.vm.exception.LoginAlreadyExistException;
import com.tsystems.javaschool.vm.exception.SBBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private UserHelper userHelper;

    @Transactional
    public String addUser(String login, Long roleId) throws SBBException {
        if (!userDAO.findByLogin(login).isEmpty()) {
            throw new LoginAlreadyExistException("User with login " + login + " already exists. Choose another login");
        }
        Role role = roleDAO.findById(roleId);
        if (role == null) {
            throw new InvalidIdException("Role doesn't exist; id: " + roleId);
        }
        String password = userHelper.generatePassword();
        User user = new User(login, userHelper.sha256(password));
        user.setRole(role);
        userDAO.create(user);
        return password;
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }
}
