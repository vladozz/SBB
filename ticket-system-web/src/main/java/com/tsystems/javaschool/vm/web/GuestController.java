package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.converter.BoardConverter;
import com.tsystems.javaschool.vm.converter.TicketConverter;
import com.tsystems.javaschool.vm.domain.Board;
import com.tsystems.javaschool.vm.domain.PairBoard;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.Ticket;
import com.tsystems.javaschool.vm.dto.BoardStationDTO;
import com.tsystems.javaschool.vm.dto.BuyTicketDTO;
import com.tsystems.javaschool.vm.dto.DefTripDTO;
import com.tsystems.javaschool.vm.dto.RespDefTripDTO;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.PassengerException;
import com.tsystems.javaschool.vm.helper.MailHelper;
import com.tsystems.javaschool.vm.helper.ResponseHelper;
import com.tsystems.javaschool.vm.service.PassengerBoardService;
import com.tsystems.javaschool.vm.service.PassengerService;
import com.tsystems.javaschool.vm.service.StationService;
import com.tsystems.javaschool.vm.service.UserService;
import com.tsystems.javaschool.vm.validator.BuyTicketDTOValidator;
import com.tsystems.javaschool.vm.validator.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class GuestController {
    private static final Logger LOG = Logger.getLogger(GuestController.class);

    @Autowired
    private StationService stationService;
    @Autowired
    private PassengerBoardService passengerBoardService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private UserService userService;

    @Autowired
    private BoardConverter boardConverter;
    @Autowired
    private TicketConverter ticketConverter;
    @Autowired
    private BuyTicketDTOValidator buyTicketDTOValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private ResponseHelper responseHelper;
    @Autowired
    private MailHelper mailHelper;


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
        map.put("stationList", stationList);
        return "req_trips";
    }

    @RequestMapping(value = "/reqtrip")
    public String showTrips() {
        return "redirect:/reqtrip/index";
    }

    @RequestMapping(value = "/reqtrip/get", method = RequestMethod.POST)
    public String getTrips(@ModelAttribute(value = "dto") DefTripDTO defTripDTO, ModelMap map,
                           HttpServletResponse response) throws EntityNotFoundException, IOException {

        List<PairBoard> pairBoards = passengerBoardService.getDefTrips(defTripDTO);
        Collections.sort(pairBoards);
        List<RespDefTripDTO> dtoList = new ArrayList<RespDefTripDTO>();
        for (PairBoard pairBoard : pairBoards) {
            int freePlaces = passengerService.countFreePlaces(pairBoard.getDeparture(), pairBoard.getArrive());
            dtoList.add(boardConverter.convertToRespDefTripDTO(pairBoard, freePlaces));
        }
        map.put("tripList", dtoList);
        return "guest/req_trips_row";


    }

    @RequestMapping(value = "/ticket/buy", method = RequestMethod.POST)
    @ResponseBody
    public String buyTicket(@ModelAttribute BuyTicketDTO buyTicketRequestDTO, @RequestParam("email") String email,
                            HttpServletResponse response)
            throws PassengerException, EntityNotFoundException, IOException {

        List<String> validationErrors = buyTicketDTOValidator.validate(buyTicketRequestDTO);
        boolean emailRequired = email != null && !email.isEmpty();
        if (emailRequired) {
            validationErrors.addAll(userValidator.validateEmail(email));
        }
        if (!validationErrors.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, responseHelper.createErrorResponse(validationErrors));
            return "";
        }


        Ticket ticket = passengerService.buyTicket(buyTicketRequestDTO);
        if (emailRequired) {
            try {
                mailHelper.sendEmail(email, ticket);
            } catch (MessagingException e) {
                LOG.error(e);
                return "Buy ticket operation success but the problem appears during sending email";
            }
        }
        return ticketConverter.convertToString(ticket);

    }
}
