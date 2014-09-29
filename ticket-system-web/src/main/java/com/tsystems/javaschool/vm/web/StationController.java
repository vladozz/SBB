package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.StationService;
import com.tsystems.javaschool.vm.validator.StationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public String listStations(ModelMap map) {
        map.put("station", new Station());
        map.put("stationList", stationService.getAllStations());
        return "station";
    }

    @RequestMapping("/removed")
    public String listRemovedStations(ModelMap map) {
        map.put("station", new Station());
        map.put("removed", true);
        map.put("stationList", stationService.getAllRemovedStations());
        return "station";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "redirect:/station/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addStation(@ModelAttribute(value = "station") Station station,
                             HttpServletResponse response, ModelMap map) {
        List<String> validationErrors = stationValidator.validate(station);
        if (!validationErrors.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            map.put("messages", validationErrors);
            return "msg";
        }

        station = stationService.addStation(station);
        map.put("station", station);
        return "station_row";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editStation(@ModelAttribute(value = "station") Station station,
                              HttpServletResponse response, ModelMap map) throws EntityNotFoundException {
        System.out.println("station = " + station);
        List<String> validationErrors = stationValidator.validate(station);
        if (!validationErrors.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            map.put("messages", validationErrors);
            return "msg";
        }


        station = stationService.editStation(station);
        map.put("station", station);
        return "station_row";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String removeStation(@RequestParam("id") Long stationId, @RequestParam("version") Integer version) {
        stationService.removeStation(stationId, version);

        return "Remove success";
    }
}
