package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.converter.BoardConverter;
import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.PairBoard;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.dto.BoardStationDTO;
import com.tsystems.javaschool.vm.dto.DefTripDTO;
import com.tsystems.javaschool.vm.dto.RespDefTripDTO;
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
public class GuestController {
    @Autowired
    private StationService stationService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardConverter boardConverter;

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

    @RequestMapping(value = "/board/get" , method = RequestMethod.POST)
     public String getBoard(@RequestParam("stationId") Long stationId,
                            @RequestParam("date") String date, Map<String, Object> map) {
        try {
            List<Board> board = boardService.getBoardForStation(stationId, date);
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
        } catch (InvalidIdException e) {
            map.put("errorList", Arrays.asList(e.getMessage()));
            return "msg/error";
        }
    }

    @RequestMapping(value = "/reqtrip/index")
    public String tripsIndex(Map<String, Object> map) {
        List<Station> stationList = stationService.getAllStations();
        Collections.sort(stationList, new Comparator<Station>() {
            @Override
            public int compare(Station o1, Station o2) {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        });
//        map.put("dto", new DefTripDTO());
        map.put("stationList", stationList);
        return "req_trips";
    }

    @RequestMapping(value = "/reqtrip")
    public String showTrips() {
        return "redirect:/reqtrip/index";
    }

    @RequestMapping(value = "/reqtrip/get", method = RequestMethod.POST)
    public String getTrips(@ModelAttribute(value="dto") DefTripDTO defTripDTO, ModelMap map) {
        //TODO:validation
        System.out.println(defTripDTO);
        try {
            List<PairBoard> pairBoards = boardService.getDefTrips(defTripDTO);
            Collections.sort(pairBoards);
            List<RespDefTripDTO> dtoList = new ArrayList<RespDefTripDTO>();
            for (PairBoard pairBoard : pairBoards) {
                dtoList.add(boardConverter.convertToRespDefTripDTO(pairBoard, 0));
            }
            map.put("tripList", dtoList);
            return "guest/req_trips_row";
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }
        return "";
    }
}
