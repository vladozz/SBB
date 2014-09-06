package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

@Controller
public class TrainController {
    private final String entity = "train";
    private final String entityWithSlash = "/" + entity;
    private final String index = entityWithSlash + "/index";
    private final String redirect = "redirect:" + index;

    @Autowired
    TrainService trainService;

    @RequestMapping(index)
    public String listTrains(Map<String, Object> map) {
        map.put("train", new Train());
        map.put("trainList", trainService.getAllTrains());


        return entity;
    }

    @RequestMapping(entityWithSlash)
    public String home() {
        return redirect;
    }

    @RequestMapping(value = entityWithSlash + "/add", method = RequestMethod.POST)
    public @ResponseBody
    String addTrain(@Valid @ModelAttribute(value = "train") Train train, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError e : result.getAllErrors()) {
                sb.append(e.getObjectName()).append(" ").append(e.getDefaultMessage()).append("\n");
            }
            return sb.toString();
        }
        
        trainService.addTrain(train);
        return train.getId().toString();
    }

    @RequestMapping(value = entityWithSlash + "/edit", method = RequestMethod.POST)
    public @ResponseBody
    String editTrain(@Valid @ModelAttribute(value = "train") Train train, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError e : result.getAllErrors()) {
                sb.append(e.getObjectName()).append(" ").append(e.getDefaultMessage()).append("\n");
            }
            return sb.toString();
        }

        trainService.editTrain(train);
        return train.getId().toString();
    }

//    @RequestMapping(value = entityWithSlash + "/delete/{id}")
//    public String removeTrain(@PathVariable("id") Long trainId) {
//
//        trainService.removeTrain(trainId);
//
//        return redirect;
//    }

    @RequestMapping(value = entityWithSlash + "/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String removeTrain(@PathVariable("id") Long trainId) {

        trainService.removeTrain(trainId);

        return "OK";
    }
}
