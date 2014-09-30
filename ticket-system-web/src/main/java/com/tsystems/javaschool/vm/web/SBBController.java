package com.tsystems.javaschool.vm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class SBBController {


    @RequestMapping("/index")
    public String mainPage(Map<String, Object> map) {
        return "index";
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }
}
