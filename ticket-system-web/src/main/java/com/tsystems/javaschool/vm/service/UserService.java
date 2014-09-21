package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.RoleDAO;
import com.tsystems.javaschool.vm.dao.UserDAO;
import com.tsystems.javaschool.vm.domain.Role;
import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.exception.InvalidIdException;
import com.tsystems.javaschool.vm.exception.LoginAlreadyExistException;
import com.tsystems.javaschool.vm.exception.SBBException;
import com.tsystems.javaschool.vm.helper.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
    public User addUser(String login, String password, Long roleId) throws SBBException {
        if (!userDAO.findByLogin(login).isEmpty()) {
            throw new LoginAlreadyExistException("User with login " + login + " already exists. Choose another login");
        }
        Role role = roleDAO.findById(roleId);
        if (role == null) {
            throw new InvalidIdException("Role doesn't exist; id: " + roleId);
        }

        User user = new User(login, userHelper.sha256(password), role);
        userDAO.create(user);
        return user;

    }

    @Transactional
    public void registerUser(String email, String password) throws SBBException {
        if (!userDAO.findByLogin(email).isEmpty()) {
            throw new LoginAlreadyExistException("User with email " + email + " already exists. Choose another login");
        }
        List<Role> roleUser = roleDAO.findByTitle("ROLE_USER");
        if (roleUser.isEmpty()) {
            throw new InvalidIdException("Role with name 'ROLE_USER' doesn't exist.");
        }
        String passwordHash = userHelper.sha256(password);
        User user = new User(email, passwordHash, roleUser.get(0));
        userDAO.create(user);
    }

    @Transactional
    public void changeUserPassword(Long userId, String password) throws InvalidIdException {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new InvalidIdException("User doesn't exists; id: " + userId);
        }
        String passwordHash = userHelper.sha256(password);
        user.setPassword(passwordHash);
        userDAO.update(user);
    }

    @Transactional
    public void removeUser(Long userId) {
        userDAO.delete(userId);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }

    public List<Role> getAllServiceRoles() {
        return roleDAO.findAllServiceRoles();
    }
}
