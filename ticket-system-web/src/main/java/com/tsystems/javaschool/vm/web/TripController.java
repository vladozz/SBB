package com.tsystems.javaschool.vm.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.TripDTO;
import com.tsystems.javaschool.vm.exception.InvalidIdException;
import com.tsystems.javaschool.vm.exception.OutdateException;
import com.tsystems.javaschool.vm.exception.SBBException;
import com.tsystems.javaschool.vm.service.PathService;
import com.tsystems.javaschool.vm.service.TrainService;
import com.tsystems.javaschool.vm.service.TripService;
import org.hibernate.dialect.Ingres10Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TripController {
    private static final String root = "trip";
    private static final String rootWithSlash = "/" + root;
    private static final String index = rootWithSlash + "/index";
    private static final String redirect = "redirect:" + index;

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
    public
    @ResponseBody
    String addTrip(@RequestParam("pathId") Long pathId, @RequestParam("trainId") Long trainId) {
        try {
            Trip trip = tripService.addTrip(pathId, trainId);
            TripDTO tripDTO = new TripDTO(trip.getId(),
                    trip.getTrain().getId(), trip.getTrain().getNumber(),
                    trip.getPath().getId(), trip.getPath().getTitle(), trip.getLastChange());
            return json.writeValueAsString(tripDTO);
        } catch (Exception e) {
            return "error " + e.toString();
        }
    }

    @RequestMapping(value = rootWithSlash + "/select", method = RequestMethod.POST)
    public
    @ResponseBody
    String selectTrip(@RequestParam("pathId") Long pathId, @RequestParam("trainId") Long trainId) {

        List<Trip> trips = tripService.getTripsByPathAndTrain(pathId, trainId);

        List<TripDTO> tripDTOs = new ArrayList<TripDTO>();
        for (Trip trip : trips) {
            TripDTO tripDTO = new TripDTO(trip.getId(),
                    trip.getTrain().getId(), trip.getTrain().getNumber(),
                    trip.getPath().getId(), trip.getPath().getTitle(), trip.getLastChange());
            tripDTOs.add(tripDTO);
        }

        try {
            return json.writeValueAsString(tripDTOs);
        } catch (JsonProcessingException e) {
            return "error " + e.toString();
        }
    }

    @RequestMapping(value = rootWithSlash + "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    String editTrip(@RequestParam("trip") String tripJSON) {
        try {
            TripDTO tripDTO = json.readValue(tripJSON, TripDTO.class);
            tripService.editTrip(tripDTO.getId(), tripDTO.getPathId(), tripDTO.getTrainId(), tripDTO.getLastChange());
            return "success";
        } catch (OutdateException e) {
            return "outdate " + e;
        } catch (Exception e) {
            return "error " + e;
        }
    }

    @RequestMapping(value = rootWithSlash + "/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    String removeTrip(@RequestParam("tripId") Long tripId, @RequestParam("lci") Integer lci) {
        try {
            tripService.removeTrip(tripId, lci);
        } catch (OutdateException e) {
            return "outdate " + e;
        } catch (Exception e) {
            return "error " + e;
        }
        return "success";
    }
}
