package com.tsystems.javaschool.vm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerHelper {
    public ModelAndView createMsg(String message) {
        return new ModelAndView("msg").addObject("message", message);
    }
}
