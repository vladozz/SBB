package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.service.StationService;
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
public class StationController {
    private final String entity = "station";
    private final String entityWithSlash = "/" + entity;
    private final String index = entityWithSlash + "/index";
    private final String redirect = "redirect:" + index;

    @Autowired
    StationService stationService;

    @RequestMapping(index)
    public String listStations(Map<String, Object> map) {
        map.put("station", new Station());
        map.put("stationList", stationService.getAllStations());

        return entity;
    }

    @RequestMapping(entityWithSlash)
    public String home() {
        return redirect;
    }

    @RequestMapping(value = entityWithSlash + "/add", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("station") Station station, BindingResult result) {

        stationService.addStation(station);

        return redirect;
    }

    @RequestMapping(value = entityWithSlash + "/delete/{id}")
    public String removeContact(@PathVariable("id") Long trainId) {



        return redirect;
    }
}
