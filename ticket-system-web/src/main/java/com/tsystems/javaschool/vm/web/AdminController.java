package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.LoginAlreadyExistException;
import com.tsystems.javaschool.vm.exception.SBBException;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.UserService;
import com.tsystems.javaschool.vm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private ResponseHelper responseHelper;

    @RequestMapping(method = RequestMethod.GET)
    public String showUsers(Map<String, Object> map) {

        map.put("roleList", userService.getAllServiceRoles());
        map.put("userList", userService.getAllUsers());

        return "user";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "login") String login,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "roleId") Long roleId, ModelMap map) {

        List<String> validationErrors = userValidator.validateLogin(login);
        validationErrors.addAll(userValidator.validatePassword(password));
        if (!validationErrors.isEmpty()) {
            map.put("errors", validationErrors);
            return "msg";
        }

        try {
            User user = userService.addUser(login, password, roleId);
            map.put("user", user);
            return "admin/user_row";
        } catch (LoginAlreadyExistException e) {
            return "error " + e;
        } catch (SBBException e) {
            return "error " + e;
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String editUserPassword(@RequestParam(value = "userId") Long userId,
                            @RequestParam(value = "password") String password) {

        if (userId < 0) {
            return "error You can't change this user";
        }

        List<String> validationErrors = userValidator.validatePassword(password);
        if (!validationErrors.isEmpty()) {
            return responseHelper.createErrorResponse(validationErrors);
        }

        try {
            userService.changeUserPassword(userId, password);
            return "success";
        } catch (EntityNotFoundException e) {
            return "error " + e;
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String removeUser(@PathVariable("id") Long userId) {
        if (userId < 0) {
            return "error You can't delete this user";
        }
        userService.removeUser(userId);
        return "";
    }
}
