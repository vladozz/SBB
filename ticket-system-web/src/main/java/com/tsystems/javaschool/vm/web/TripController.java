package com.tsystems.javaschool.vm.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.TripDTO;
import com.tsystems.javaschool.vm.service.PathService;
import com.tsystems.javaschool.vm.service.TrainService;
import com.tsystems.javaschool.vm.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TripController {
    private final String root = "trip";
    private final String rootWithSlash = "/" + root;
    private final String index = rootWithSlash + "/index";
    private final String redirect = "redirect:" + index;

    @Autowired
    TrainService trainService;
    @Autowired
    PathService pathService;
    @Autowired
    TripService tripService;

    ObjectMapper json = new ObjectMapper();

    @RequestMapping(index)
    public String listTrains(Map<String, Object> map) {

        map.put("trainList", trainService.getAllTrains());
        map.put("pathList", pathService.getAllPaths());

        return root;
    }

    @RequestMapping(rootWithSlash)
    public String home() {
        return redirect;
    }

    @RequestMapping(value = rootWithSlash + "/add", method = RequestMethod.POST)
    public @ResponseBody
    String addTrip(@RequestParam("pathId") Long pathId, @RequestParam("trainId") Long trainId) {
        Trip trip = tripService.addTrip(pathId, trainId);
        TripDTO tripDTO = new TripDTO(trip.getId(),
                trip.getTrain().getId(), trip.getTrain().getNumber(),
                trip.getPath().getId(), trip.getPath().getTitle());
        try {
            return json.writeValueAsString(tripDTO);
        } catch (JsonProcessingException e) {
            return "error";
        }
    }

    @RequestMapping(value = rootWithSlash + "/select", method = RequestMethod.POST)
    public @ResponseBody
    String selectTrip(@RequestParam("pathId") Long pathId, @RequestParam("trainId") Long trainId) {

        List<Trip> trips = tripService.getTripsByPathAndTrain(pathId, trainId);


        List<TripDTO> tripDTOs = new ArrayList<TripDTO>();
        for (Trip trip : trips) {
            TripDTO tripDTO = new TripDTO(trip.getId(),
                    trip.getTrain().getId(), trip.getTrain().getNumber(),
                    trip.getPath().getId(), trip.getPath().getTitle());
            tripDTOs.add(tripDTO);
        }

        try {
            return json.writeValueAsString(tripDTOs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "error";
    }

    @RequestMapping(value = rootWithSlash + "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    String editTrain(@Valid @ModelAttribute(value = "train") Train train, BindingResult result) {

        if (result.hasErrors()) {
            return "";
        }

        trainService.editTrain(train);
        return train.getId().toString();
    }

    @RequestMapping(value = rootWithSlash + "/delete/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    String removeTrain(@PathVariable("id") Long trainId) {
        trainService.removeTrain(trainId);

        return "";
    }
}
