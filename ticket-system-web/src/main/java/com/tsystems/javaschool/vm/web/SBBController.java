package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
