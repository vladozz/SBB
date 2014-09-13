package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.exception.PathException;
import com.tsystems.javaschool.vm.service.PathService;
import com.tsystems.javaschool.vm.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
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
        Collections.sort(stationList, new Comparator<Station>() {
            @Override
            public int compare(Station o1, Station o2) {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        });
        //map.put("path", new Path());
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
                                   @RequestParam("stationBeforeInsertId") Long stationBeforeInsertId, @RequestParam("lci") Integer lci, Map<String, Object> map) {
        try {
            pathService.addStationToPathSafe(pathId, stationId, stationBeforeInsertId, lci);
        } catch (PathException e) {
            return "false";
        }
        return "true";
    }

    @RequestMapping(value = rootWithSlash + "/remove", method = RequestMethod.POST)
    public @ResponseBody
    String removeStationFromPath(@RequestParam("pathId") Long pathId, @RequestParam("stationId") Long stationId,
                                        @RequestParam("lci") Integer lci, Map<String, Object> map) {

        try {
            pathService.removeStationFromPathSafe(pathId, stationId, lci);
        } catch (PathException e) {
            return "false";
        }
        List<Path> pathList = pathService.getAllPaths();
        List<Station> pathStationList = pathService.getStationsOfPath(pathId);
        return "true";
    }
}
