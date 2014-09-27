package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.converter.PathConverter;
import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.dto.PathDTO;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.OutdateException;
import com.tsystems.javaschool.vm.service.PathService;
import com.tsystems.javaschool.vm.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    @Autowired
    private PathConverter pathConverter;

    @RequestMapping("/index")
    public String listPaths(ModelMap map) {
        map.put("path", new PathDTO());
        List<PathDTO> pathDTOs = new ArrayList<PathDTO>();
        for (Path path : pathService.getAllPaths()) {
            PathDTO pathDTO = pathConverter.convertToPathDTO(path);
//            List<Station> pathStationList = pathService.getStationsOfPath(path);
//            if (!pathStationList.isEmpty()) {
//                pathDTO.setBeginStation(pathStationList.get(0).getTitle());
//                pathDTO.setEndStation(pathStationList.get(pathStationList.size() - 1).getTitle());
//            }
            pathDTOs.add(pathDTO);
        }
        map.put("pathList", pathDTOs);

        return "path";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "redirect:/path/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPath(@ModelAttribute(value = "path") Path path, ModelMap map) {

        pathService.addPath(path);
        map.put("path", pathConverter.convertToPathDTO(path));
        return "path_row";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPath(@ModelAttribute Path path, ModelMap map) throws EntityNotFoundException, OutdateException {

        Path newPath = pathService.editPath(path);
        map.put("path", pathConverter.convertToPathDTO(newPath));
        return "path_row";

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String removePath(@PathVariable("id") Long pathId) {
        pathService.removePath(pathId);

        return "";
    }
}
