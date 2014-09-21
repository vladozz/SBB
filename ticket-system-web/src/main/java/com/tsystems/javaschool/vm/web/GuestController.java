package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.exception.InvalidIdException;
import com.tsystems.javaschool.vm.exception.LoginAlreadyExistException;
import com.tsystems.javaschool.vm.exception.SBBException;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.BoardService;
import com.tsystems.javaschool.vm.service.StationService;
import com.tsystems.javaschool.vm.service.UserService;
import com.tsystems.javaschool.vm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/board")
public class GuestController {
    @Autowired
    private StationService stationService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private ResponseHelper responseHelper;

    @RequestMapping(value = "/index")
    public String index(Map<String, Object> map) {
        List<Station> stationList = stationService.getAllStations();
        Collections.sort(stationList, new Comparator<Station>() {
            @Override
            public int compare(Station o1, Station o2) {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        });
        map.put("stationList", stationList);
        return "board";
    }

    @RequestMapping(value = "")
    public String showIndex(Map<String, Object> map) {

        return "redirect:/board/index";
    }

    @RequestMapping(value = "/get")
    public String getBoard(@RequestParam("stationId") Long stationId,
                           @RequestParam("date") String date, Map<String, Object> map) {
        try {
            List<Board> boardList = boardService.getBoardForStation(stationId, date);
            map.put("boardList", boardList);
            return "board_row";
        } catch (InvalidIdException e) {
            map.put("errorList", Arrays.asList(e.getMessage()));
            return "error";
        }
    }

}
