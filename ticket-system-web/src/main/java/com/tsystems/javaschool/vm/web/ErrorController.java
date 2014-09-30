package com.tsystems.javaschool.vm.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
