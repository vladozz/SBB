package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.dto.PathDTO;
import com.tsystems.javaschool.vm.service.PathService;
import com.tsystems.javaschool.vm.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/path")
public class PathController {

    @Autowired
    private PathService pathService;
    @Autowired
    private StationService stationService;

    @RequestMapping("/index")
    public String listPaths(Map<String, Object> map) {
        map.put("path", new PathDTO());
        List<PathDTO> pathDTOs = new ArrayList<PathDTO>();
        for (Path path : pathService.getAllPaths()) {
            PathDTO pathDTO = new PathDTO(path.getId(), path.getTitle(), " ", " ", path.getLastChange());
            List<Station> pathStationList = pathService.getStationsOfPath(path.getId());
            if (!pathStationList.isEmpty()) {
                pathDTO.setBeginStation(pathStationList.get(0).getTitle());
                pathDTO.setEndStation(pathStationList.get(pathStationList.size() - 1).getTitle());
            }
            pathDTOs.add(pathDTO);
        }
        map.put("pathList", pathDTOs);

        return "path";
    }

    @RequestMapping("")
    public String home() {
        return "redirect:/path/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public
    String addPath(@Valid @ModelAttribute(value = "path") PathDTO pathDTO, BindingResult result) {

        if (result.hasErrors()) {
            return "";
        }
        Path path = new Path(pathDTO.getTitle());
        pathService.addPath(path);
        return path.getId().toString();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public
    String editPath(@Valid @ModelAttribute(value = "path") Path path, BindingResult result) {

        if (result.hasErrors()) {
            return "";
        }

        if (pathService.editPath(path)) {
            return path.getId().toString();
        } else {
            return "0";
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public
    String removePath(@PathVariable("id") Long pathId) {
        pathService.removePath(pathId);

        return "";
    }
}
