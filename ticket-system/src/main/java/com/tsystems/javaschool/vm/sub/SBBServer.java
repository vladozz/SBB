package com.tsystems.javaschool.vm.sub;

import com.tsystems.javaschool.vm.dao.*;
import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.service.BoardService;
import com.tsystems.javaschool.vm.service.PassengerService;
import com.tsystems.javaschool.vm.service.PathService;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

public class SBBServer {
    ServerSocket serverSocket;

    BoardService boardService;
    PassengerService passengerService;
    PathService pathService;

    public SBBServer() {
        EntityManager em = Persistence.createEntityManagerFactory("SBBPU").createEntityManager();

        BoardDAO boardDAO = new BoardDAO(em);
        PassengerDAO passengerDAO = new PassengerDAO(em);
        PathDAO pathDAO = new PathDAO(em);
        StationDAO stationDAO = new StationDAO(em);
        TrainDAO trainDAO = new TrainDAO(em);
        TripDAO tripDAO = new TripDAO(em);
        TicketDAO ticketDAO = new TicketDAO(em);

        boardService = new BoardService(boardDAO, tripDAO);
        passengerService = new PassengerService(passengerDAO, ticketDAO);
        pathService = new PathService(pathDAO, stationDAO, trainDAO);
    }

    public static void main(String[] args) throws Exception {
        SBBServer server = new SBBServer();
        server.addPaths();
        server.addStations();
        server.addTrains();
        server.linkPathAndStations();

    }

    public void addPaths() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("paths.txt"));

        while (in.hasNextLine()) {
            pathService.addPath(in.nextLine());
        }
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
                pathService.addStationToPath( i * 2 + 1, s);
            }
            for (Long s : Hasher.reverseArray(array[(int) i])) {
                pathService.addStationToPath( i * 2 + 2, s);
            }
        }
    }

    public void start() {


        int port = 6574;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.serverSocket = serverSocket;
            while (true) {
                Socket incoming = serverSocket.accept();
                Runnable r = new ConnectionThread(incoming);
                Thread t = new Thread(r);
                t.start();
            }
        } catch (IOException e) {
            //TODO: change to Logger
            System.out.println(e.getClass() + " " + e.getMessage() + ". Port number: " + port);
        }
    }

    public void stop() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                //TODO: add Logger
            }
        }
    }
}
