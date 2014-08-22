package com.tsystems.javaschool.vm.sub;

import com.tsystems.javaschool.vm.domain.Passenger;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.dto.PassengerDTO;
import com.tsystems.javaschool.vm.dto.StationDTO;
import com.tsystems.javaschool.vm.dto.TrainDTO;
import com.tsystems.javaschool.vm.protocol.ManagerCommand;
import com.tsystems.javaschool.vm.protocol.StartRequest;
import com.tsystems.javaschool.vm.dto.LoginDTO;
import com.tsystems.javaschool.vm.protocol.ServerResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
                out.writeObject(ServerResponse.InvalidCommand);
            }
            incoming.shutdownOutput();
            out.close();
            incoming.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //TODO: реализация
    }

    public void sessionManager(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Object o;
        Long sessionID = in.readLong();
        if (server.containsSession(sessionID)) {
            o = in.readObject();
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
                    //TODO:
                }
            } else {
                out.writeObject(ServerResponse.InvalidCommand);
            }
        } else {
            out.writeObject(ServerResponse.InvalidSession);
        }
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
            if (station == null) {
                out.writeObject(ServerResponse.FailedCreation);
            } else {
                out.writeObject(ServerResponse.CreationSuccess);
                out.writeObject(station.getId());
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
            if (train == null) {
                out.writeObject(ServerResponse.FailedCreation);
            } else {
                out.writeObject(ServerResponse.CreationSuccess);
                out.writeObject(train.getId());
            }
        } else {
            out.writeObject(ServerResponse.InvalidInput);
        }
    }
    //TODO: сделать synchronyzed другой метод
    public synchronized void loginManager(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        LoginDTO loginDTO = (LoginDTO) in.readObject();
        String login = loginDTO.getLogin();
        String password = loginDTO.getPassword();
        Map.Entry<Long, String> session = server.getUserService().checkLoginAndCreateSession(login, password);
        if (session == null) {
            out.writeObject(ServerResponse.InvalidLoginOrPassword);
        } else {
            server.addSession(session.getKey(), session.getValue());
            out.writeObject(session.getKey());
        }
    }
}
