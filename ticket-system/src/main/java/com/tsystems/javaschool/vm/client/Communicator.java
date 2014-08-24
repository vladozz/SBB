package com.tsystems.javaschool.vm.client;

import com.tsystems.javaschool.vm.domain.Passenger;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.dto.*;
import com.tsystems.javaschool.vm.exception.ConnectionException;
import com.tsystems.javaschool.vm.exception.InvalidIdException;
import com.tsystems.javaschool.vm.exception.InvalidLoginOrPasswordException;
import com.tsystems.javaschool.vm.protocol.ClientCommand;
import com.tsystems.javaschool.vm.protocol.ManagerCommand;
import com.tsystems.javaschool.vm.protocol.ServerResponse;
import com.tsystems.javaschool.vm.protocol.StartRequest;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Communicator {

    public static Long loginAction(LoginDTO loginDTO) throws InvalidLoginOrPasswordException, IOException {
        try(Socket socket = new Socket("localhost", 6574)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(StartRequest.LoginManager);
            out.writeObject(loginDTO);
            out.flush();

            Object o = in.readObject();

            if (o instanceof ServerResponse) {
                ServerResponse response = ((ServerResponse) o);
                switch (response) {
                    case OperationSuccess:
                        Long session = ((Long) in.readObject());
                        socket.close();
                        return session;
                    case InvalidLoginOrPassword:
                        throw new InvalidLoginOrPasswordException();
                    case InvalidInput:
                        throw new RuntimeException("Invalid Input");
                    case InvalidStartRequest:
                        throw new RuntimeException("Invalid Input");
                    default:
                        throw new RuntimeException("Unknown ServerRequest");
                }
            } else {
                throw new RuntimeException("Unknown ServerRequest");
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static Long addTrainAction(String number, Short placesQty, Long session) throws IOException {
        try(Socket socket = new Socket("localhost", 6574)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(StartRequest.SessionManager);
            out.writeLong(session);
            out.writeObject(ManagerCommand.AddTrain);
            out.writeObject(new TrainDTO(number, placesQty));
            out.flush();

            Object o = in.readObject();

            if (o instanceof ServerResponse) {
                ServerResponse response = ((ServerResponse) o);
                switch (response) {
                    case CreationSuccess:
                        Long trainID = ((Long) in.readObject());
                        socket.close();
                        return trainID;
                    case FailedCreation:
                        return null;
                    case InvalidInput:
                        throw new RuntimeException("Invalid Input");
                    case InvalidStartRequest:
                        throw new RuntimeException("Invalid Start Request");
                    default:
                        throw new RuntimeException("Unknown Server Request");
                }
            } else {
                throw new RuntimeException("Unknown Server Request");
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static Long addStationAction(String number, TimeZone timeZone, Long session) throws IOException {
        try(Socket socket = new Socket("localhost", 6574)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(StartRequest.SessionManager);
            out.writeLong(session);
            out.writeObject(ManagerCommand.AddStation);
            out.writeObject(new StationDTO(number, timeZone));
            out.flush();

            Object o = in.readObject();

            if (o instanceof ServerResponse) {
                ServerResponse response = ((ServerResponse) o);
                switch (response) {
                    case CreationSuccess:
                        Long stationID = ((Long) in.readObject());
                        socket.close();
                        return stationID;
                    case FailedCreation:
                        return null;
                    case InvalidInput:
                        throw new RuntimeException("Invalid Input");
                    case InvalidStartRequest:
                        throw new RuntimeException("Invalid Start Request");
                    default:
                        throw new RuntimeException("Unknown Server Request");
                }
            } else {
                throw new RuntimeException("Unknown Server Request");
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static List<TrainDTO> getAllTrainsAction(Long session) throws IOException {
        try(Socket socket = new Socket("localhost", 6574)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(StartRequest.SessionManager);
            out.writeLong(session);
            out.writeObject(ManagerCommand.GetAllTrains);
            out.flush();

            Object o = in.readObject();

            if (o instanceof ServerResponse) {
                ServerResponse response = ((ServerResponse) o);
                switch (response) {
                    case OperationSuccess:
                        List<TrainDTO> list =((List<TrainDTO>) in.readObject());
                        socket.close();
                        return list;
                    case FailedOperation:
                        return null;
                    case InvalidInput:
                        throw new RuntimeException("Invalid Input");
                    case InvalidStartRequest:
                        throw new RuntimeException("Invalid Start Request");
                    default:
                        throw new RuntimeException("Unknown Server Request");
                }
            } else {
                throw new RuntimeException("Unknown Server Request");
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static List<PassengerDTO> getAllPassengersByTripAction(Long tripId, Long session) throws IOException, InvalidIdException {
        try(Socket socket = new Socket("localhost", 6574)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(StartRequest.SessionManager);
            out.writeLong(session);
            out.writeObject(ManagerCommand.GetPassengersOfTrip);
            out.writeObject(tripId);
            out.flush();

            Object o = in.readObject();

            if (o instanceof ServerResponse) {
                ServerResponse response = ((ServerResponse) o);
                switch (response) {
                    case OperationSuccess:
                        List<PassengerDTO> list =((List<PassengerDTO>) in.readObject());
                        socket.close();
                        return list;
                    case InvalidId:
                        throw new InvalidIdException();
                    case InvalidInput:
                        throw new RuntimeException("Invalid Input");
                    case InvalidStartRequest:
                        throw new RuntimeException("Invalid Start Request");
                    default:
                        throw new RuntimeException("Unknown Server Request");
                }
            } else {
                throw new RuntimeException("Unknown Server Request");
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            RuntimeException e2 = new RuntimeException("Type is not List<PassengerDRO>");
            e2.addSuppressed(e1);
            throw e2;
        }
    }

    public static List<ReqDefTripDTO> getDefTripsAction(String departureStation, String arriveStation, Timestamp departureTime, Timestamp arriveTime) throws IOException, InvalidIdException {
        DefTripDTO defTripDTO = new DefTripDTO(arriveStation, departureStation, departureTime, arriveTime);
        System.out.println("defTripDTO = " + defTripDTO);
        try (Socket socket = new Socket("localhost", 6574)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(StartRequest.EnterClient);
            out.writeObject(ClientCommand.GetDefTrips);
            out.writeObject(defTripDTO);
            out.flush();

            Object o = in.readObject();

            if (o instanceof ServerResponse) {
                ServerResponse response = ((ServerResponse) o);
                switch (response) {
                    case OperationSuccess:
                        List<ReqDefTripDTO> list = ((List<ReqDefTripDTO>) in.readObject());
                        socket.close();
                        return list;
                    case InvalidId:
                        throw new InvalidIdException();
                    case InvalidInput:
                        throw new RuntimeException("Invalid Input");
                    case InvalidStartRequest:
                        throw new RuntimeException("Invalid Start Request");
                    default:
                        throw new RuntimeException("Unknown Server Request");
                }
            } else {
                throw new RuntimeException("Unknown Server Request");
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            RuntimeException e2 = new RuntimeException("Type is not List<ReqDefTripDTO>");
            e2.addSuppressed(e1);
            throw e2;
        }
    }

    public static List<BoardStationDTO> getBoardForStationAction(String stationTitle, Timestamp date) throws IOException, InvalidIdException {
        try(Socket socket = new Socket("localhost", 6574)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(StartRequest.EnterClient);
            out.writeObject(ClientCommand.GetBoardForStation);
            out.writeObject(stationTitle);
            out.writeObject(date);
            out.flush();

            Object o = in.readObject();

            if (o instanceof ServerResponse) {
                ServerResponse response = ((ServerResponse) o);
                switch (response) {
                    case OperationSuccess:
                        List<BoardStationDTO> list =((List<BoardStationDTO>) in.readObject());
                        socket.close();
                        return list;
                    case InvalidId:
                        throw new InvalidIdException();
                    case InvalidInput:
                        throw new RuntimeException("Invalid Input");
                    case InvalidStartRequest:
                        throw new RuntimeException("Invalid Start Request");
                    default:
                        throw new RuntimeException("Unknown Server Request");
                }
            } else {
                throw new RuntimeException("Unknown Server Request");
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            RuntimeException e2 = new RuntimeException("Type is not List<BoardStationDTO>");
            e2.addSuppressed(e1);
            throw e2;
        }
    }

    public static TicketDTO buyTicketAction(String firstName, String lastName, Calendar birthDate,
                                                        Long departureBoardId, 
                                                        Long arriveBoardId) throws Exception {

        PassengerDTO passengerDTO = new PassengerDTO(firstName, lastName, birthDate);
        BuyTicketDTO buyTicketDTO = new BuyTicketDTO(passengerDTO, departureBoardId, arriveBoardId);
        try(Socket socket = new Socket("localhost", 6574)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(StartRequest.EnterClient);
            out.writeObject(ClientCommand.BuyTicket);
            out.writeObject(buyTicketDTO);
            out.flush();

            Object o = in.readObject();

            if (o instanceof ServerResponse) {
                ServerResponse response = ((ServerResponse) o);
                switch (response) {
                    case OperationSuccess:
                        TicketDTO ticketDTO =((TicketDTO) in.readObject());
                        socket.close();
                        return ticketDTO;
                    case Exception:
                        throw ((Exception) in.readObject());
                    case InvalidId:
                        throw new InvalidIdException("Invalid ID");
                    case InvalidInput:
                        throw new RuntimeException("Invalid Input");
                    case InvalidStartRequest:
                        throw new RuntimeException("Invalid Start Request");
                    default:
                        throw new RuntimeException("Unknown Server Request");
                }
            } else {
                throw new RuntimeException("Unknown Server Request");
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            RuntimeException e2 = new RuntimeException("Type is not List<BoardStationDTO>");
            e2.addSuppressed(e1);
            throw e2;
        }
    }
}
