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

    @Autowired
    TrainService trainService;

    @RequestMapping("/index")
    public String listTrains(Map<String, Object> map) {
        map.put("train", new Train());


        return "train";
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("train") Train train, BindingResult result) {

        trainService.addTrain(train);

        return "redirect:/index";
    }

    @RequestMapping(value = "/delete/{trainId}")
    public String removeContact(@PathVariable("trainId") Long trainId) {

        trainService.removeTrain(trainId);

        return "redirect:/index";
    }
}
