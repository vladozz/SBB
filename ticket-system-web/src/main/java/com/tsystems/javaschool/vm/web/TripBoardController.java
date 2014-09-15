package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.exception.PathException;
import com.tsystems.javaschool.vm.service.BoardService;
import com.tsystems.javaschool.vm.service.PathService;
import com.tsystems.javaschool.vm.service.StationService;
import com.tsystems.javaschool.vm.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class TripBoardController {
    private static final String root = "trip/board";
    private static final String rootWithSlash = "/" + root;


    @Autowired
    TripService tripService;
    @Autowired
    BoardService boardService;


    @RequestMapping(value = rootWithSlash + "/{id}", method = RequestMethod.GET)
    public String showBoard(@PathVariable("id") Long tripId, Map<String, Object> map) {

        map.put("tripId", tripId);
        Trip trip = tripService.findById(tripId);
        map.put("lci", trip.getLastChange());

        return root;
    }

    @RequestMapping(value = rootWithSlash + "/create", method = RequestMethod.GET)
    public
    @ResponseBody
    String createBoard(@RequestParam("tripId") Long tripId, @RequestParam("lci") Integer lci,
                       @RequestParam("date") String date) {

        Trip trip = tripService.findById(tripId);

        return root;
    }


}
