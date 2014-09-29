package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.LoginAlreadyExistException;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.UserService;
import com.tsystems.javaschool.vm.validator.LongValidator;
import com.tsystems.javaschool.vm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping("/404")
        public String error404() {
        return "404";
    }

    @RequestMapping("/400")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String error400() {
        return "msg";
    }

}
