package com.tsystems.javaschool.vm.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.BoardTripDTO;
import com.tsystems.javaschool.vm.exception.OutdateException;
import com.tsystems.javaschool.vm.exception.SBBException;
import com.tsystems.javaschool.vm.service.BoardService;
import com.tsystems.javaschool.vm.service.TripService;
import com.tsystems.javaschool.vm.sub.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class TripBoardController {
    private static final String root = "trip/board";
    private static final String rootWithSlash = "/" + root;

    private ObjectMapper json = new ObjectMapper();

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

    @RequestMapping(value = rootWithSlash + "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    String createBoard(@RequestParam("tripId") Long tripId, @RequestParam("lci") Integer lci,
                       @RequestParam("date") String date) {

        try {
            List<Board> board = boardService.createEmptyBoard(tripId, date, lci);
            List<BoardTripDTO> boardDTOs = new ArrayList<BoardTripDTO>();
            for (Board b : board) {
                boardDTOs.add(Adapter.convertToBoardTripDTO(b));
            }
            return json.writeValueAsString(boardDTOs);
        } catch (OutdateException e) {
            return "outdate " + e;
        } catch (SBBException e) {
            return "error " + e;
        } catch (Throwable e) {
            return "error " + e;
        }
    }

    @RequestMapping(value = rootWithSlash + "/select", method = RequestMethod.POST)
    public
    @ResponseBody
    String getBoard(@RequestParam("tripId") Long tripId) {
        try {
            List<Board> board = boardService.getBoardForTrip(tripId);
            List<BoardTripDTO> boardDTOs = new ArrayList<BoardTripDTO>();
            for (Board b : board) {
                boardDTOs.add(Adapter.convertToBoardTripDTO(b));
            }
            return json.writeValueAsString(boardDTOs);
        } catch (SBBException e) {
            return "error " + e;
        } catch (Throwable e) {
            return "error " + e;
        }
    }

    @RequestMapping(value = rootWithSlash + "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    String getBoard(@RequestParam("board") String jsonString) {
        try {
            BoardTripDTO[] boardTripDTOs = json.readValue(jsonString, BoardTripDTO[].class);
            String response = boardService.changeBoard(boardTripDTOs);
            if (response.equals("")) {
                return "success";
            } else {
                return "error " + response;
            }
        } catch (Throwable e) {
            return "error " + e;
        }
    }
}
