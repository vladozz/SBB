package com.tsystems.javaschool.vm.web;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.vm.converter.BoardConverter;
import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.BoardTripDTO;
import com.tsystems.javaschool.vm.exception.*;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.BoardService;
import com.tsystems.javaschool.vm.service.PassengerService;
import com.tsystems.javaschool.vm.service.TripService;
import com.tsystems.javaschool.vm.validator.DateTimeStringValidator;
import com.tsystems.javaschool.vm.validator.LongValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/trip/board")
public class TripBoardController {

    @Autowired
    private TripService tripService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private PassengerService passengerService;


    @Autowired
    private ObjectMapper json;
    @Autowired
    private BoardConverter boardConverter;
    @Autowired
    private LongValidator longValidator;
    @Autowired
    private DateTimeStringValidator dateTimeValidator;
    @Autowired
    private ResponseHelper responseHelper;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showBoard(@PathVariable("id") Long tripId, Map<String, Object> map) throws EntityNotFoundException {

        map.put("tripId", tripId);
        Trip trip = tripService.findById(tripId);
        map.put("lci", trip.getLastChange());

        return "trip/board";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    String createBoard(@RequestParam("tripId") Long tripId,
                       @RequestParam("date") String date, HttpServletResponse response)
            throws IOException, TripException, EmptyListException, OutdateException, EntityNotFoundException {

        List<String> validationErrors = longValidator.validateLong(tripId, "TripId");
        validationErrors.addAll(dateTimeValidator.validateDateString(date));
        if (!validationErrors.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, responseHelper.createErrorResponse(validationErrors));
        }

        List<Board> board = boardService.createEmptyBoard(tripId, date);
        List<BoardTripDTO> boardDTOs = new ArrayList<BoardTripDTO>();
        for (Board b : board) {
            boardDTOs.add(boardConverter.convertToBoardTripDTO(b));
        }
        return json.writeValueAsString(boardDTOs);

    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public
    @ResponseBody
    String getBoard(@RequestParam("tripId") Long tripId, HttpServletResponse response) throws EntityNotFoundException, JsonProcessingException {

        List<String> validationErrors = longValidator.validateLong(tripId, "TripId");
        if (!validationErrors.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseHelper.createErrorResponse(validationErrors);
        }

        List<Board> board = boardService.getBoardForTrip(tripId);
        List<BoardTripDTO> boardDTOs = new ArrayList<BoardTripDTO>();
        for (Board b : board) {
            boardDTOs.add(boardConverter.convertToBoardTripDTO(b));
        }
        return json.writeValueAsString(boardDTOs);

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editBoard(@RequestParam("board") String jsonString, ModelMap map, HttpServletResponse response)
            throws EntityNotFoundException, IOException, EmptyListException, TripException {

        BoardTripDTO[] boardTripDTOs = json.readValue(jsonString, BoardTripDTO[].class);
        List<String> errors = boardService.changeBoard(boardTripDTOs);
        if (errors.isEmpty()) {
            map.put("messages", "Update success");
            return "msg";
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            map.put("messages", errors);
            return "msg";
        }

    }

}
