package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.OutdateException;
import com.tsystems.javaschool.vm.exception.PathException;
import com.tsystems.javaschool.vm.service.PathService;
import com.tsystems.javaschool.vm.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/path/stations")
public class PathStationController {

    @Autowired
    private PathService pathService;
    @Autowired
    private StationService stationService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String pathStations(@PathVariable("id") Long pathId, Map<String, Object> map) throws EntityNotFoundException {
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
        map.put("pathId", pathId);
        map.put("lastChange", pathService.findById(pathId).getLastChange());
        map.put("pathList", pathList);
        map.put("pathStationList", pathStationList);
        map.put("stationList", stationList);
        return "path/stations";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addStationToPath(@RequestParam("pathId") Long pathId, @RequestParam("stationId") Long stationId,
                                   @RequestParam("stationBeforeInsertId") Long stationBeforeInsertId,
                                   @RequestParam("lci") Integer lci, ModelMap map) throws EntityNotFoundException, OutdateException {

        Station station = pathService.addStationToPathSafe(pathId, stationId, stationBeforeInsertId, lci);
        map.put("station", station);
        return "path_station_row";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeStationFromPath(@RequestParam("pathId") Long pathId, @RequestParam("stationId") Long stationId,
                                        @RequestParam("lci") Integer lci, Map<String, Object> map) throws EntityNotFoundException {
        try {
            pathService.removeStationFromPathSafe(pathId, stationId, lci);
        } catch (OutdateException e) {
            return "false";
        }
        return "true";
    }
}
