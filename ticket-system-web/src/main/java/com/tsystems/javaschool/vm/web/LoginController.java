package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.exception.LoginAlreadyExistException;
import com.tsystems.javaschool.vm.exception.SBBException;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.UserService;
import com.tsystems.javaschool.vm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    UserValidator userValidator;
    @Autowired
    ResponseHelper responseHelper;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public
    @ResponseBody
    String register(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {

        List<String> validationErrors = userValidator.validateEmail(email);
        validationErrors.addAll(userValidator.validatePassword(password));
        if (!validationErrors.isEmpty()) {
            return responseHelper.createErrorResponse(validationErrors);
        }

        try {
            userService.registerUser(email, password);
            return "success";
        } catch (LoginAlreadyExistException e) {
            return "error " + e;
        } catch (SBBException e) {
            return "Server error";
        }
    }

}


