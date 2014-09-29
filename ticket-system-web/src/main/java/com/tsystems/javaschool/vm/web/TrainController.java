package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.TrainService;
import com.tsystems.javaschool.vm.validator.TrainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.util.List;

@Controller
@RequestMapping("/train")
public class TrainController {

    @Autowired
    private TrainService trainService;
    @Autowired
    private TrainValidator trainValidator;
    @Autowired
    private ResponseHelper responseHelper;


    @RequestMapping("/index")
    public String listTrains(ModelMap map) {

        map.put("train", new Train());
        map.put("trainList", trainService.getAllTrains());
        return "train";
    }

    @RequestMapping("/removed")
    public String listRemovedTrains(ModelMap map) {

        map.put("train", new Train());
        map.put("removed", true);
        map.put("trainList", trainService.getAllRemovedTrains());
        return "train";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "redirect:/train/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTrain(@ModelAttribute(value = "train") Train train,
                           HttpServletResponse response, ModelMap map) {
        List<String> validationErrors = trainValidator.validate(train);
        if (!validationErrors.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            map.put("messages", validationErrors);
            return "msg";
        }

        train = trainService.addTrain(train);
        map.put("train", train);
        return "train_row";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editTrain(@ModelAttribute(value = "train") Train train,
                           HttpServletResponse response ,ModelMap map) throws EntityNotFoundException {
        List<String> validationErrors = trainValidator.validate(train);
        if (!validationErrors.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            map.put("messages", validationErrors);
            return "msg";
        }


        train = trainService.editTrain(train);
        map.put("train", train);
        return "train_row";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String removeTrain(@RequestParam("id") Long trainId, @RequestParam("version") Integer version) {
        trainService.removeTrain(trainId, version);

        return "Remove success";
    }

}
