package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.RoleDAO;
import com.tsystems.javaschool.vm.dao.UserDAO;
import com.tsystems.javaschool.vm.domain.Role;
import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.InvalidPasswordException;
import com.tsystems.javaschool.vm.exception.LoginAlreadyExistException;
import com.tsystems.javaschool.vm.exception.SBBException;
import com.tsystems.javaschool.vm.helper.UserHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private UserHelper userHelper;

    @Transactional
    public User addUser(String login, String password, Long roleId) throws LoginAlreadyExistException, EntityNotFoundException {
        if (userDAO.ifExists(login)) {
            throw new LoginAlreadyExistException("User with login " + login + " already exists. Choose another login");
        }
        Role role = roleDAO.findById(roleId);

        User user = new User(login, userHelper.sha256(password), role);
        userDAO.create(user);
        return user;

    }

    @Transactional
    public void registerUser(String email, String password) throws SBBException {
        if (userDAO.ifExists(email)) {
            throw new LoginAlreadyExistException("User with email " + email + " already exists. Choose another login.");
        }
        List<Role> roleUser = roleDAO.findByTitle("USER");
        String passwordHash = userHelper.sha256(password);
        User user = new User(email, passwordHash, roleUser.get(0));
        userDAO.create(user);
    }

    @Transactional
    public void changeUserPassword(Long userId, String password) throws EntityNotFoundException {
        User user = userDAO.findById(userId);
        String passwordHash = userHelper.sha256(password);
        user.setPassword(passwordHash);
        userDAO.update(user);
    }

    @Transactional
    public void changePassword(String currentPassword, String newPassword) throws EntityNotFoundException, InvalidPasswordException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDAO.findByLogin(username);
        String currentPasswordHash = userHelper.sha256(currentPassword);
        if (!currentPasswordHash.equals(user.getPassword())) {
            throw new InvalidPasswordException();
        }
        String newPasswordHash = userHelper.sha256(newPassword);
        user.setPassword(newPasswordHash);
        userDAO.update(user);
    }

    @Transactional
    public void removeUser(Long userId) {
        userDAO.delete(userId, 0);
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
