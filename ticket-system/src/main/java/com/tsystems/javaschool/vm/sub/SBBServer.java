package com.tsystems.javaschool.vm.sub;

import com.tsystems.javaschool.vm.client.StartForm;

import com.tsystems.javaschool.vm.dao.*;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.dto.BoardTripDTO;
import com.tsystems.javaschool.vm.exception.DifferentArrayException;
import com.tsystems.javaschool.vm.service.BoardService;
import com.tsystems.javaschool.vm.service.PassengerService;
import com.tsystems.javaschool.vm.service.PathService;
import com.tsystems.javaschool.vm.service.UserService;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

public class SBBServer {
    private static Logger logger = Logger.getLogger(SBBServer.class);
    private ServerSocket serverSocket;
    //TODO: reset sessions in time
    private Map<Long, String> sessions;

    private BoardService boardService;
    private PassengerService passengerService;
    private PathService pathService;
    private UserService userService;

    public SBBServer() {
        EntityManager em = Persistence.createEntityManagerFactory("SBBPU").createEntityManager();

        BoardDAO boardDAO = new BoardDAO(em);
        PassengerDAO passengerDAO = new PassengerDAO(em);
        PathDAO pathDAO = new PathDAO(em);
        StationDAO stationDAO = new StationDAO(em);
        TrainDAO trainDAO = new TrainDAO(em);
        TripDAO tripDAO = new TripDAO(em);
        TicketDAO ticketDAO = new TicketDAO(em);
        UserDAO userDAO = new UserDAO(em);

        boardService = new BoardService(boardDAO, tripDAO, pathDAO, trainDAO);
        passengerService = new PassengerService(passengerDAO, ticketDAO, boardDAO, stationDAO, tripDAO);
        pathService = new PathService(pathDAO, stationDAO, trainDAO);
        userService = new UserService(userDAO);

        sessions = new HashMap<>();
    }

    public static void main(String[] args) {
        StartForm startForm = new StartForm();
        SBBServer server = new SBBServer();
        server.start();

    }

    public void addPaths() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("paths.txt"));

        while (in.hasNextLine()) {
            pathService.addPath(in.nextLine());
        }
    }

    public void addPassengers() {
        passengerService.addPassenger("Vladimir", "Putin", new GregorianCalendar(2014, Calendar.AUGUST, 21));
        passengerService.addPassenger("Dmitriy", "Medvedev", new GregorianCalendar(2014, Calendar.AUGUST, 21));
        passengerService.addPassenger("German", "Gref", new GregorianCalendar(2014, Calendar.AUGUST, 21));
        passengerService.addPassenger("Barak", "Obama", new GregorianCalendar(2014, Calendar.AUGUST, 21));
        passengerService.addPassenger("Sid", "Vicious", new GregorianCalendar(2014, Calendar.AUGUST, 21));
        passengerService.addPassenger("Ozzy", "Osbourne", new GregorianCalendar(2014, Calendar.AUGUST, 21));
        passengerService.addPassenger("Brad", "Pitt", new GregorianCalendar(2014, Calendar.AUGUST, 21));
        passengerService.addPassenger("Hannibal", "Lektor", new GregorianCalendar(2014, Calendar.AUGUST, 21));
    }

    public void addStations() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("stations.txt"));

        while (in.hasNextLine()) {
            pathService.addStation(in.nextLine(), TimeZone.getDefault());
        }

    }

    public void addTrains() {
        pathService.addTrain("11", (short) 60);
        pathService.addTrain("12", (short) 120);
        pathService.addTrain("13", (short) 120);
        pathService.addTrain("14", (short) 60);
    }

    private void linkPathAndStations() throws Exception {
        long [][] array = {{1, 2, 4, 3, 10, 11}, {1, 5, 6, 13, 12},{2, 14, 6, 7}, {4, 2, 4, 3, 10, 11} };
        for (long i = 0; i < 4; i++) {
            for (Long s : array[(int) i]) {
                pathService.addStationToPath( i + 1, s + 17);
            }
            for (Long s : Hasher.reverseArray(array[(int) i])) {
                pathService.addStationToPath( i * 2 + 2, s + 17);
            }
        }
    }

    public void addMiniBoard() {
        final long HOUR = 1000L * 60 * 60;
        final long TEN_MINUTES = 1000L * 60 * 10;
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Trip trip = boardService.addTrip(1L, 32L);
        List<Timestamp> departures = new ArrayList<>();
        List<Timestamp> arrives = new ArrayList<>();
        for (int i = 0; i < trip.getPath().getStations().size(); i++) {
            arrives.add(timestamp);
            timestamp = new Timestamp(timestamp.getTime() + TEN_MINUTES);
            departures.add(timestamp);
            timestamp = new Timestamp(timestamp.getTime() + HOUR);
        }
        try {
            boardService.generateBoardByTrip(trip, arrives, departures);
        } catch (DifferentArrayException e) {
            logger.warn("Program exception! " , e);
        }
    }



    public void start() {


        int port = 6574;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.serverSocket = serverSocket;
            while (true) {
                Socket incoming = serverSocket.accept();
                Runnable r = new ConnectionThread(incoming, this);
                Thread t = new Thread(r);
                t.start();
            }
        } catch (Exception e) {
            logger.error("Error of opening ServerSocket! Port number:" +  port  + "  " + e);
        }
    }

    public void stop() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.error("Error of closing ServerSocket! ", e);
            }
        }
    }

    public void addSession(Long id, String login) {

        sessions.put(id, login);
    }

    public boolean containsSession(Long id) {
        return sessions.containsKey(id);
    }

    public BoardService getBoardService() {
        return boardService;
    }

    public PassengerService getPassengerService() {
        return passengerService;
    }

    public PathService getPathService() {
        return pathService;
    }

    public UserService getUserService() {
        return userService;
    }
}
