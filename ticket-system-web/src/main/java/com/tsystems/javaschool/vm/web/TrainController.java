package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.TrainService;
import com.tsystems.javaschool.vm.validator.TrainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.util.List;
import java.util.Map;

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
    public String listTrains(Map<String, Object> map) {

        map.put("train", new Train());
        map.put("trainList", trainService.getAllTrains());
        return "train";
    }

    @RequestMapping("")
    public String home() {
        return "redirect:/train/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addTrain(@ModelAttribute(value = "train") Train train, BindingResult result, HttpServletResponse response) {
        List<String> validationErrors = trainValidator.validate(train);
        if (!validationErrors.isEmpty()) {
            return responseHelper.createErrorResponse(validationErrors);
        }

        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            return "result not ok";
        }

        trainService.addTrain(train);
        return train.getId().toString();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String editTrain(@Valid @ModelAttribute(value = "train") Train train) {

        trainService.editTrain(train);
        return train.getId().toString();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String removeTrain(@PathVariable("id") Long trainId) {
        trainService.removeTrain(trainId);

        return "";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String handleRuntimeExceptions(Exception e) {
        //e.printStackTrace();
        return e.getCause().toString();
    }

}
