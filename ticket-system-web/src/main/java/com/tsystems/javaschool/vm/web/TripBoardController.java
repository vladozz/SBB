package com.tsystems.javaschool.vm.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.vm.converter.BoardConverter;
import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.BoardTripDTO;
import com.tsystems.javaschool.vm.exception.OutdateException;
import com.tsystems.javaschool.vm.exception.SBBException;
import com.tsystems.javaschool.vm.service.BoardService;
import com.tsystems.javaschool.vm.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/trip/board")
public class TripBoardController {

    @Autowired
    private ObjectMapper json;
    @Autowired
    private BoardConverter boardConverter;
    @Autowired
    private TripService tripService;
    @Autowired
    private BoardService boardService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showBoard(@PathVariable("id") Long tripId, Map<String, Object> map) {

        map.put("tripId", tripId);
        Trip trip = tripService.findById(tripId);
        map.put("lci", trip.getLastChange());

        return "trip/board";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    String createBoard(@RequestParam("tripId") Long tripId, @RequestParam("lci") Integer lci,
                       @RequestParam("date") String date) {

        try {
            List<Board> board = boardService.createEmptyBoard(tripId, date, lci);
            List<BoardTripDTO> boardDTOs = new ArrayList<BoardTripDTO>();
            for (Board b : board) {
                boardDTOs.add(boardConverter.convertToBoardTripDTO(b));
            }
            return json.writeValueAsString(boardDTOs);
        } catch (OutdateException e) {
            return "outdate " + e;
        } catch (SBBException e) {
            return "error " + e;
        } catch (Exception e) {
            return "error " + e;
        }
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public
    @ResponseBody
    String getBoard(@RequestParam("tripId") Long tripId) {
        try {
            List<Board> board = boardService.getBoardForTrip(tripId);
            List<BoardTripDTO> boardDTOs = new ArrayList<BoardTripDTO>();
            for (Board b : board) {
                boardDTOs.add(boardConverter.convertToBoardTripDTO(b));
            }
            return json.writeValueAsString(boardDTOs);
        } catch (SBBException e) {
            return "error " + e;
        } catch (Exception e) {
            return "error " + e;
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
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
        } catch (Exception e) {
            return "error " + e;
        }
    }
}
