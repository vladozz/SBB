package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.LoginAlreadyExistException;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.UserService;
import com.tsystems.javaschool.vm.validator.LongValidator;
import com.tsystems.javaschool.vm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private LongValidator longValidator;
    @Autowired
    private ResponseHelper responseHelper;

    @RequestMapping(method = RequestMethod.GET)
    public String showUsers(ModelMap map) {
        map.put("roleList", userService.getAllServiceRoles());
        map.put("userList", userService.getAllUsers());

        return "user";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "login") String login,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "roleId") Long roleId, ModelMap map) throws EntityNotFoundException, LoginAlreadyExistException {

        List<String> validationErrors = userValidator.validateLogin(login);
        validationErrors.addAll(userValidator.validatePassword(password));
        validationErrors.addAll(longValidator.validateLong(roleId, "Role"));
        if (!validationErrors.isEmpty()) {
            map.put("errors", validationErrors);
            return "msg";
        }

        User user = userService.addUser(login, password, roleId);
        map.put("user", user);
        return "admin/user_row";

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUserPassword(@RequestParam(value = "userId") Long userId,
                                   @RequestParam(value = "password") String password, ModelMap map,
                                   HttpServletResponse response) throws IOException, EntityNotFoundException {

        List<String> validationErrors = longValidator.validateLong(userId, "UserId");
        validationErrors.addAll(userValidator.validatePassword(password));
        if (!validationErrors.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            map.put("messages", validationErrors);
            return "msg";
        }
        if (userId < 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            map.put("messages", "You can't edit this user");
            return "msg";
        }

        userService.changeUserPassword(userId, password);
        map.put("messages", "Password changed successfully");
        return "msg";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String removeUser(@PathVariable("id") Long userId, HttpServletResponse response, ModelMap map) throws IOException {

        if (userId < 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            map.put("messages", "You can't delete this user");
            return "msg";
        }

        userService.removeUser(userId);
        map.put("msg", "Deleted successful");
        return "msg";
    }
}
