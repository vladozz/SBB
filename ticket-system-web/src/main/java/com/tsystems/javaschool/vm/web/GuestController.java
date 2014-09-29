package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.converter.BoardConverter;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.dto.*;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.PassengerException;
import com.tsystems.javaschool.vm.service.PassengerBoardService;
import com.tsystems.javaschool.vm.service.PassengerService;
import com.tsystems.javaschool.vm.service.StationService;
import com.tsystems.javaschool.vm.validator.LongValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class GuestController {
    @Autowired
    private StationService stationService;
    @Autowired
    private PassengerBoardService passengerBoardService;
    @Autowired
    private PassengerService passengerService;

    @Autowired
    private BoardConverter boardConverter;

    @Autowired
    private LongValidator longValidator;

    @RequestMapping(value = "/board/index")
    public String boardIndex(Map<String, Object> map) {
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

    @RequestMapping(value = "/board")
    public String showBoard(Map<String, Object> map) {

        return "redirect:/board/index";
    }

    @RequestMapping(value = "/board/get", method = RequestMethod.POST)
    public String getBoard(@RequestParam("stationId") Long stationId,
                           @RequestParam("date") String date, Map<String, Object> map) throws EntityNotFoundException {


        List<Board> board = passengerBoardService.getBoardForStation(stationId, date);
        Collections.sort(board, new Comparator<Board>() {
            @Override
            public int compare(Board o1, Board o2) {
                return o1.getArriveTime().compareTo(o2.getArriveTime());
            }
        });
        List<BoardStationDTO> boardStationDTOs = new ArrayList<BoardStationDTO>();
        for (Board b : board) {
            boardStationDTOs.add(boardConverter.convertToBoardStationDTO(b));
        }
        map.put("boardList", boardStationDTOs);
        return "guest/board_row";

    }

    @RequestMapping(value = "/reqtrip/index")
    public String tripsIndex(Map<String, Object> map) {
        List<Station> stationList = stationService.getAllStations();
        Collections.sort(stationList);
//        map.put("dto", new DefTripDTO());
        map.put("stationList", stationList);
        return "req_trips";
    }

    @RequestMapping(value = "/reqtrip")
    public String showTrips() {
        return "redirect:/reqtrip/index";
    }

    @RequestMapping(value = "/reqtrip/get", method = RequestMethod.POST)
    public String getTrips(@ModelAttribute(value = "dto") DefTripDTO defTripDTO, ModelMap map) {
        //TODO:validation
        try {
            List<PairBoard> pairBoards = passengerBoardService.getDefTrips(defTripDTO);
            Collections.sort(pairBoards);
            List<RespDefTripDTO> dtoList = new ArrayList<RespDefTripDTO>();
            for (PairBoard pairBoard : pairBoards) {
                int freePlaces = passengerService.countFreePlaces(pairBoard.getDeparture(), pairBoard.getArrive());
                dtoList.add(boardConverter.convertToRespDefTripDTO(pairBoard, freePlaces));
            }
            map.put("tripList", dtoList);
            return "guest/req_trips_row";
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "/ticket/buy", method = RequestMethod.POST)
    @ResponseBody
    public String buyTicket(@ModelAttribute BuyTicketDTO buyTicketRequestDTO) {
        //TODO: dto validation
        try {
            System.out.println("buyTicketRequestDTO = " + buyTicketRequestDTO);
            Ticket ticket = passengerService.buyTicket(buyTicketRequestDTO);
            System.out.println("ticket.toString() = " + ticket.toString());
            return ticket.toString();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return "error " + e;
        } catch (PassengerException e) {
            e.printStackTrace();
            return "error " + e;
        }
    }
}
