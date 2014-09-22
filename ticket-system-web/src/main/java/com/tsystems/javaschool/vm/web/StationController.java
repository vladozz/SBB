package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.StationService;
import com.tsystems.javaschool.vm.validator.StationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/station")
public class StationController {

    @Autowired
    private StationService stationService;
    @Autowired
    private StationValidator stationValidator;
    @Autowired
    private ResponseHelper responseHelper;

    @RequestMapping("/index")
    public String listStations(Map<String, Object> map) {
        map.put("station", new Station());
        map.put("stationList", stationService.getAllStations());
        return "station";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "redirect:/station/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addStation(@ModelAttribute Station station) {

        List<String> validationErrors = stationValidator.validate(station);
        if (!validationErrors.isEmpty()) {
            return responseHelper.createErrorResponse(validationErrors);
        }

        stationService.addStation(station);
        return station.getId().toString();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String editTrain(@ModelAttribute Station station) {

        List<String> validationErrors = stationValidator.validate(station);
        if (!validationErrors.isEmpty()) {
            return responseHelper.createErrorResponse(validationErrors);
        }

        stationService.editStation(station);
        return station.getId().toString();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String removeTrain(@PathVariable("id") Long trainId) {

        stationService.removeStation(trainId);
        return "";
    }
}
