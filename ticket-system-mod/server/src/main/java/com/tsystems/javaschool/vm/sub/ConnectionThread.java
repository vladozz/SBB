package com.tsystems.javaschool.vm.sub;


import com.tsystems.javaschool.vm.domain.*;
import com.tsystems.javaschool.vm.dto.*;
import com.tsystems.javaschool.vm.exception.*;
import com.tsystems.javaschool.vm.protocol.ClientCommand;
import com.tsystems.javaschool.vm.protocol.ManagerCommand;
import com.tsystems.javaschool.vm.protocol.ServerResponse;
import com.tsystems.javaschool.vm.protocol.StartRequest;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectionThread implements Runnable {
    private static Logger logger = Logger.getLogger(ConnectionThread.class);
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
                logger.warn("Protocol error! Invalid Start Request: " +  o.getClass() + " " + o);
                out.writeObject(ServerResponse.InvalidStartRequest);
            }
            out.flush();
            incoming.close();
        } catch (IOException e) {
            logger.error("Error", e);
        } catch (ClassNotFoundException e) {
            logger.error("Error", e);
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
        } else {
            logger.warn("Protocol error! Invalid Client Command: " +  o.getClass() + " " + o);
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
            logger.warn("Protocol error! Invalid Input! Expected: DefTripDTO. Found " + o.getClass() + " " + o);
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
                    TicketDTO ticketDTO = new TicketDTO(ticket.getId());
                    out.writeObject(ServerResponse.OperationSuccess);
                    out.writeObject(ticketDTO);
                } else {
                    out.writeObject(ServerResponse.InvalidId);
                }
            } catch (InvalidIdException | TenMinutesException | AlreadyOnTripException | OutOfFreeSpacesException e) {
                out.writeObject(ServerResponse.Exception);
                out.writeObject(e);
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
            logger.warn("Protocol error! Invalid Input! Expected: BuyTicketDTO. Found " + o.getClass() + " " + o);
        }
    }

    private void getBoardForStation(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        final long DAY = 1000L * 60 * 60 * 24;
        Object o = in.readObject();
        Object o2 = in.readObject();

        if (o instanceof String && o2 instanceof Timestamp) {
            String stationTitle = (String) o;
            Timestamp date = ((Timestamp) o2);
            Timestamp before = new Timestamp(date.getTime() + DAY);
            List<Board> boardList = server.getBoardService().getBoardForStation(stationTitle, date, before);
            if (boardList != null) {
                List<BoardStationDTO> boardStationDTOs = new ArrayList<>();
                for (Board b : boardList) {
                    BoardStationDTO boardStationDTO = new BoardStationDTO(b.getTrip().getId(),
                            b.getTrip().getPath().getTitle(),
                            b.getTrip().getTrain().getNumber(),
                            b.getArriveTime(),
                            (int) (b.getDepartureTime().getTime() - b.getArriveTime().getTime()) / 60000,
                            b.getDepartureTime());
                    boardStationDTOs.add(boardStationDTO);
                }
                out.writeObject(ServerResponse.OperationSuccess);
                out.writeObject(boardStationDTOs);
            } else {
                out.writeObject(ServerResponse.InvalidId);
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
            logger.warn("Protocol error! Invalid Input! Expected: String & Timestamp. Found " + o.getClass() + " " + o
                    + " " + o2.getClass() + "  " + o2);
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
                    case GetAllPaths:
                        getAllPaths(in, out);
                        break;
                    case GetStationsOfPath:
                        getStationsOfPath(in, out);
                        break;
                    case InsertStationIntoPath:
                        insertStationIntoPath(in, out);
                        break;
                    case RemoveStationFromPath:
                        removeStationFromPath(in, out);
                    //TODO: add my logic
                    default:
                        break;
                }
            } else {
                out.writeObject(ServerResponse.InvalidManagerCommand);
                logger.warn("Protocol error! Invalid Manager Command: " + o.getClass() + " " + o);
            }
        } else {
            out.writeObject(ServerResponse.InvalidSession);
        }
    }

    private void removeStationFromPath(ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        String pathTitle = ((String) in.readObject());
        Integer index = ((Integer) in.readObject());
        try {
            Path path =  server.getPathService().removeStationFromPath(pathTitle, index);
            if (path != null) {
                out.writeObject(ServerResponse.OperationSuccess);
            } else {
                out.writeObject(ServerResponse.FailedOperation);
            }
        } catch (InvalidIndexException e) {
            out.writeObject(ServerResponse.Exception);
            out.writeObject(e);
        }
    }

    private void insertStationIntoPath(ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        String pathTitle = ((String) in.readObject());
        String stationToBeAdded = ((String) in.readObject());
        Integer index = ((Integer) in.readObject());
        try {
            Path path =  server.getPathService().addStationToPath(pathTitle, stationToBeAdded, index);
            if (path != null) {
                out.writeObject(ServerResponse.OperationSuccess);
            } else {
                out.writeObject(ServerResponse.FailedOperation);
            }
        } catch (InvalidIndexException e) {
            out.writeObject(ServerResponse.Exception);
            out.writeObject(e);
        }
    }

    private void getStationsOfPath(ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof String) {
            String pathTitle = ((String) o);
            List<Station> stations = server.getPathService().getStationsOfPath(pathTitle);
            if (stations != null) {
                List<StationDTO> stationDTOs = new ArrayList<>();
                for (Station s : stations) {
                    StationDTO stationDTO = new StationDTO(s.getId(), s.getTitle(), s.getTimeZone());
                    stationDTOs.add(stationDTO);
                }
                out.writeObject(ServerResponse.OperationSuccess);
                out.writeObject(stationDTOs);
            } else {
                out.writeObject(ServerResponse.InvalidId);
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
            logger.warn("Protocol error! Invalid Input! Expected: String. Found " + o.getClass() + " " + o);
        }
    }

    private void getAllPaths(ObjectInputStream in, ObjectOutputStream out) throws IOException {
        List<Path> paths = server.getPathService().getAllPaths();
        if (paths != null) {
            List<PathDTO> pathDTOs = new ArrayList<>();
            for (Path p : paths) {
                PathDTO pathDTO = new PathDTO(p.getId(), p.getTitle());
                pathDTOs.add(pathDTO);
            }
            out.writeObject(ServerResponse.OperationSuccess);
            out.writeObject(pathDTOs);
        } else {
            out.writeObject(ServerResponse.FailedOperation);
        }
    }

    private void getAllTrains(ObjectInputStream in, ObjectOutputStream out) throws IOException {
        List<Train> trains = server.getPathService().getAllTrains();
        List<TrainDTO> trainDTOs = new ArrayList<>();
        if (trains != null) {
            for (Train t : trains) {
                TrainDTO trainDTO = new TrainDTO(t.getId(), t.getNumber(), t.getPlacesQty());
                trainDTOs.add(trainDTO);
            }
            out.writeObject(ServerResponse.OperationSuccess);
            out.writeObject(trainDTOs);
        } else {
            out.writeObject(ServerResponse.FailedOperation);
        }
    }

    private void getPassengersOfTrip(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof Long) {
            Long tripId = ((Long) o);
            List<Passenger> passengers = server.getPassengerService().getPassengersOfTripById(tripId);
            if (passengers != null) {
                List<PassengerDTO> passengerDTOs = new ArrayList<PassengerDTO>();
                if (passengers != null) {
                    for (Passenger p : passengers) {
                        PassengerDTO passengerDTO = new PassengerDTO(p.getId(), p.getFirstName(), p.getLastName(), p.getBirthDate());
                        passengerDTOs.add(passengerDTO);
                    }

                    out.writeObject(ServerResponse.OperationSuccess);
                    out.writeObject(passengerDTOs);
                } else {
                    out.writeObject(ServerResponse.FailedOperation);
                }
            } else {
                out.writeObject(ServerResponse.InvalidId);
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
            logger.warn("Protocol error! Invalid Input! Expected: Long. Found " + o.getClass() + " " + o);
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
            logger.warn("Protocol error! Invalid Input! Expected: StationDTO. Found " + o.getClass() + " " + o);
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
            logger.warn("Protocol error! Invalid Input! Expected: DefTripDTO. Found " + o.getClass() + " " + o);
        }
    }

    private void addTrain(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o = in.readObject();
        if (o instanceof TrainDTO) {
            TrainDTO trainDTO = ((TrainDTO) o);
            Train train = server.getPathService().addTrain(trainDTO.getNumber(), trainDTO.getPlacesQty());
            if (train != null) {
                out.writeObject(ServerResponse.CreationSuccess);
                out.writeObject(train.getId());
            } else {
                out.writeObject(ServerResponse.FailedCreation);
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
            logger.warn("Protocol error! Invalid Input! Expected: TrainDTO. Found " + o.getClass() + " " + o);
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
                logger.info(login + " logged in. SessionId = " + session);
                server.addSession(session.getKey(), session.getValue());
                out.writeObject(ServerResponse.OperationSuccess);
                out.writeObject(session.getKey());
            } else {
                out.writeObject(ServerResponse.InvalidLoginOrPassword);
                logger.info("Invalid login \"" + login + "\" or password \"" + password + "\"");
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
            logger.warn("Protocol error! Invalid Input! Expected: LOginDTO. Found " + o.getClass() + " " + o);
        }
    }
}
