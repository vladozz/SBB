package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class TrainController {
    private static final String root = "train";
    private static final String rootWithSlash = "/" + root;
    private static final String index = rootWithSlash + "/index";
    private static final String redirect = "redirect:" + index;

    @Autowired
    TrainService trainService;

    @RequestMapping(index)
    public String listTrains(Map<String, Object> map) {
        map.put("train", new Train());

        map.put("trainList", trainService.getAllTrains());


        return root;
    }

    @RequestMapping(rootWithSlash)
    public String home() {
        return redirect;
    }

    @RequestMapping(value = rootWithSlash + "/add", method = RequestMethod.POST)
    public @ResponseBody
    String addTrain(@Valid @ModelAttribute(value = "train") Train train, BindingResult result) {

        if (result.hasErrors()) {
            return "result not ok";
        }
        
        trainService.addTrain(train);
        return train.getId().toString();
    }

    @RequestMapping(value = rootWithSlash + "/edit", method = RequestMethod.POST)
    public @ResponseBody
    String editTrain(@Valid @ModelAttribute(value = "train") Train train, BindingResult result) {

        if (result.hasErrors()) {
            return "";
        }

        trainService.editTrain(train);
        return train.getId().toString();
    }

    @RequestMapping(value = rootWithSlash + "/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String removeTrain(@PathVariable("id") Long trainId) {
        trainService.removeTrain(trainId);

        return "";
    }
}
