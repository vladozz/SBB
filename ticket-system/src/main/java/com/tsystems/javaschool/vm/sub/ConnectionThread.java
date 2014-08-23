package com.tsystems.javaschool.vm.sub;


import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.dto.*;
import com.tsystems.javaschool.vm.protocol.ClientCommand;
import com.tsystems.javaschool.vm.protocol.ManagerCommand;
import com.tsystems.javaschool.vm.protocol.StartRequest;
import com.tsystems.javaschool.vm.protocol.ServerResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectionThread implements Runnable {
    private Socket incoming;
    private SBBServer server;


    public ConnectionThread(Socket incoming, SBBServer server) {
        this.incoming = incoming;
        this.server = server;
    }

    @Override
    public void run() {
        try(ObjectInputStream in = new ObjectInputStream(incoming.getInputStream())){
            ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());
            Object o = in.readObject();
            if (o instanceof StartRequest) {
                StartRequest startRequest = ((StartRequest) o);
                switch (startRequest) {
                    case EnterClient:
                        enterClient(in, out);
                        break;
                    case LoginManager:
                        loginManager(in, out);
                        break;
                    case SessionManager:
                        sessionManager(in, out);
                        break;
                    default:
                        break;
                }
            } else {
                out.writeObject(ServerResponse.InvalidStartRequest);
            }
            out.flush();
            incoming.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void enterClient(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof ClientCommand) {
            ClientCommand clientCommand = ((ClientCommand) o);
            switch (clientCommand) {
                case GetBoardForStation:
                    getBoardForStation(in, out);
                    break;
                case BuyTicket:
                    buyTicket(in, out);
                    break;
                case GetDefTrips:
                    getDefTrips(in, out);
                    break;
                default:
                    break;
            }
        }
    }

    private void getDefTrips(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof DefTripDTO) {
            DefTripDTO defTripDTO = ((DefTripDTO) o);
            List<PairBoard> pairBoards = server.getBoardService().getDefTrips(defTripDTO.getDepartureStation(),
                    defTripDTO.getArriveStation(), defTripDTO.getDepartureTime(), defTripDTO.getArriveTime());
            List<ReqDefTripDTO> reqDefTripDTOList = new ArrayList<>();
            for (PairBoard pairBoard : pairBoards) {
                Trip trip = pairBoard.getArrive().getTrip();
                ReqDefTripDTO reqDefTripDTO = new ReqDefTripDTO(trip.getId(),
                        trip.getTrain().getNumber(), trip.getPath().getTitle(),
                        pairBoard.getDeparture().getId(), pairBoard.getDeparture().getStation().getTitle(), pairBoard.getDeparture().getDepartureTime(),
                        pairBoard.getArrive().getId(), pairBoard.getArrive().getStation().getTitle(), pairBoard.getArrive().getArriveTime());
                reqDefTripDTOList.add(reqDefTripDTO);
            }
            out.writeObject(ServerResponse.OperationSuccess);
            out.writeObject(reqDefTripDTOList);
        } else {
            out.writeObject(ServerResponse.InvalidInput);
        }
    }

    private void buyTicket(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof BuyTicketDTO) {
            BuyTicketDTO buyTicketDTO = ((BuyTicketDTO) o);
            PassengerDTO passengerDTO = buyTicketDTO.getPassengerDTO();
            try {
                Ticket ticket = server.getPassengerService().buyTicket(passengerDTO.getFirstName(), passengerDTO.getLastName(),
                        passengerDTO.getBirthDate(), buyTicketDTO.getDepartureBoardId(), buyTicketDTO.getArriveBoardId());
                if (ticket != null) {
                    //TODO:
                    //out.writeObject(ServerResponse.OperationSuccess);
                    //out.writeObject();
                } else {
                    out.writeObject(ServerResponse.InvalidId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //TODO: make different classes of Exception
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
        }
    }

    private void getBoardForStation(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        Object o2 = in.readObject();
        Object o3 = in.readObject();
        if (o instanceof String && o2 instanceof Timestamp && o3 instanceof Timestamp) {
            String stationTitle = (String) o;
            Timestamp after = ((Timestamp) o2);
            Timestamp before = ((Timestamp) o3);
            List<Board> boardList = server.getBoardService().getBoardForStation(stationTitle, after, before);
            List<BoardStationDTO> boardStationDTOs = new ArrayList<>();
            for (Board b : boardList) {
                BoardStationDTO boardStationDTO = new BoardStationDTO(b.getTrip().getId(),
                        b.getTrip().getPath().getTitle(),
                        b.getTrip().getTrain().getNumber(),
                        b.getArriveTime(),
                        (int)(b.getDepartureTime().getTime() - b.getArriveTime().getTime()) / 60000,
                        b.getDepartureTime());
                boardStationDTOs.add(boardStationDTO);
            }
            out.writeObject(ServerResponse.OperationSuccess);
            out.writeObject(boardStationDTOs);
        } else {
            out.writeObject(ServerResponse.InvalidInput);
        }
    }

    public void sessionManager(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Long sessionID = in.readLong();
        if (server.containsSession(sessionID)) {
            Object o = in.readObject();
            if (o instanceof ManagerCommand) {
                ManagerCommand mc = ((ManagerCommand) o);
                switch (mc) {
                    case AddStation:
                        addStation(in, out);
                        break;
                    case AddTrain:
                        addTrain(in, out);
                        break;
                    case GetPassengersOfTrip:
                        getPassengersOfTrip(in, out);
                        break;
                    case GetAllTrains:
                        getAllTrains(in, out);
                        break;
                    //TODO: add my logic
                    default:
                        break;
                }
            } else {
                out.writeObject(ServerResponse.InvalidManagerCommand);
            }
        } else {
            out.writeObject(ServerResponse.InvalidSession);
        }
    }

    private void getAllTrains(ObjectInputStream in, ObjectOutputStream out) throws IOException {
        List<Train> trains = server.getPathService().getAllTrains();
        List<TrainDTO> trainDTOs = new ArrayList<>();
        for (Train t : trains) {
            TrainDTO trainDTO = new TrainDTO(t.getNumber(), t.getPlacesQty());
            trainDTOs.add(trainDTO);
        }
        out.writeObject(ServerResponse.OperationSuccess);
        out.writeObject(trainDTOs);
    }

    private void getPassengersOfTrip(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof Long) {
            Long tripId = ((Long) o);
            List<Passenger> passengers = server.getPassengerService().getPassengersOfTripById(tripId);
            if (passengers != null) {
                List<PassengerDTO> passengerDTOs = new ArrayList<PassengerDTO>();
                for (Passenger p : passengers) {
                    PassengerDTO passengerDTO = new PassengerDTO(p.getFirstName(), p.getLastName(), p.getBirthDate());
                    passengerDTOs.add(passengerDTO);
                }
                out.writeObject(ServerResponse.OperationSuccess);
                out.writeObject(passengerDTOs);
            } else {
                out.writeObject(ServerResponse.InvalidId);
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
        }

    }

    private void addStation(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof StationDTO) {
            StationDTO stationDTO = ((StationDTO) o);
            Station station = server.getPathService().addStation(stationDTO.getTitle(), stationDTO.getTimeZone());
            if (station != null) {
                out.writeObject(ServerResponse.CreationSuccess);
                out.writeObject(station.getId());
            } else {
                out.writeObject(ServerResponse.FailedCreation);
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
        }
    }

    public void getBoardForTrip(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof Long) {
            Long tripId = ((Long) o);
            List<Board> boardList = server.getBoardService().getBoardForTrip(tripId);
            if (boardList != null) {
                List<BoardTripDTO> boardTripDTOList = new ArrayList<>();
                for (Board b : boardList) {
                    BoardTripDTO boardTripDTO = new BoardTripDTO(
                            b.getId(), b.getStation().getTitle(), b.getArriveTime(), b.getDepartureTime());
                    boardTripDTOList.add(boardTripDTO);
                }
                out.writeObject(ServerResponse.OperationSuccess);
                out.writeObject(tripId);
                out.writeObject(boardTripDTOList);
            } else {
                out.writeObject(ServerResponse.InvalidId);
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
        }
    }

    private void addTrain(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof TrainDTO) {
            TrainDTO trainDTO = ((TrainDTO) o);
            Train train = server.getPathService().addTrain(trainDTO.getNumber(), trainDTO.getPlacesQty());
            if (train != null) {
                out.writeObject(ServerResponse.CreationSuccess);
                out.writeLong(train.getId());
            } else {
                out.writeObject(ServerResponse.FailedCreation);
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
        }
    }
    //TODO: сделать synchronized другой метод
    public synchronized void loginManager(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof LoginDTO) {
            LoginDTO loginDTO = (LoginDTO) o;
            String login = loginDTO.getLogin();
            String password = loginDTO.getPassword();
            Map.Entry<Long, String> session = server.getUserService().checkLoginAndCreateSession(login, password);
            if (session != null) {
                server.addSession(session.getKey(), session.getValue());
                out.writeObject(ServerResponse.OperationSuccess);
                System.out.println("session.getKey() = " + session.getKey());
                out.writeObject(session.getKey());
            } else {
                out.writeObject(ServerResponse.InvalidLoginOrPassword);
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
        }
    }
}
