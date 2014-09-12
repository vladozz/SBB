package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.exception.PathException;
import com.tsystems.javaschool.vm.service.PathService;
import com.tsystems.javaschool.vm.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class PathStationController {
    private final String root = "path/stations";
    private final String rootWithSlash = "/" + root;
    private final String index = rootWithSlash + "/index";
    private final String redirect = "redirect:" + rootWithSlash + "/";

    @Autowired
    PathService pathService;
    @Autowired
    StationService stationService;


    @RequestMapping(value = rootWithSlash + "/{id}", method = RequestMethod.GET)
    public String pathStations(@PathVariable("id") Long pathId, Map<String, Object> map) {
        List<Path> pathList = pathService.getAllPaths();
        List<Station> pathStationList = pathService.getStationsOfPath(pathId);
        List<Station> stationList = stationService.getAllStations();
        stationList.removeAll(pathStationList);
        map.put("path", new Path());
        map.put("pathId", pathId);
        map.put("lci", pathService.findById(pathId).getLastChange());
        map.put("pathList", pathList);
        map.put("pathStationList", pathStationList);
        map.put("stationList", stationList);
        return root;
    }

    @RequestMapping(value = rootWithSlash, method = RequestMethod.POST)
    public @ResponseBody
    String addStationToPath(@RequestParam("pathId") Long pathId, @RequestParam("stationId") Long stationId,
                                   @RequestParam("index") int index, @RequestParam("lci") Integer lci, Map<String, Object> map) {
        try {
            pathService.addStationToPath(pathId, stationId, index, lci);
        } catch (PathException e) {
            return "false";
        }
/*        List<Path> pathList = pathService.getAllPaths();
        List<Station> pathStationList = pathService.getStationsOfPath(pathId);
        List<Station> stationList = stationService.getAllStations();
        stationList.removeAll(pathStationList);
        map.put("path", new Path());
        map.put("pathId", pathId);
        map.put("pathList", pathList);
        map.put("pathStationList", pathStationList);
        map.put("stationList", stationList);*/
        return "true";
    }

    @RequestMapping(value = rootWithSlash + "/remove", method = RequestMethod.POST)
    public @ResponseBody
    String removeStationFromPath(@RequestParam("pathId") Long pathId, @RequestParam("index") int index,
                                        @RequestParam("lci") Integer lci, Map<String, Object> map) {

        try {
            pathService.removeStationFromPath(pathId, index, lci);
        } catch (PathException e) {
            return "false";
        }
        List<Path> pathList = pathService.getAllPaths();
        List<Station> pathStationList = pathService.getStationsOfPath(pathId);
/*        List<Station> stationList = stationService.getAllStations();
        stationList.removeAll(pathStationList);
        map.put("path", new Path());
        map.put("pathId", pathId);
        map.put("pathList", pathList);
        map.put("pathStationList", pathStationList);
        map.put("stationList", stationList);*/
        return "true";
    }
}
