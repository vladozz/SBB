package com.tsystems.javaschool.vm.sub;

import com.tsystems.javaschool.vm.protocol.Command;
import com.tsystems.javaschool.vm.dto.LoginDTO;
import com.tsystems.javaschool.vm.protocol.ServerRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
        try {
            ObjectInputStream in = new ObjectInputStream(incoming.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());
            Object o = in.readObject();
            if (o instanceof Command) {
                Command command = ((Command) o);
                switch (command) {
                    case EnterClient:

                        break;
                    case LoginManager:
                        LoginManager(in, out);
                        break;
                    case SessionManager:
                        Long sessionID = in.readLong();
                        if (server.containsSession(sessionID)) {

                        } else {
                            out.writeObject(ServerRequest.InvalidSession);
                        }
                    default:
                        break;
                }
            } else {
                out.writeObject(ServerRequest.InvalidCommand);
            }
            incoming.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //TODO: реализация
    }

    public void LoginManager(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        LoginDTO loginDTO = (LoginDTO) in.readObject();
        String login = loginDTO.getLogin();
        String password = loginDTO.getPassword();
        Map.Entry<Long, String> session = server.userService.checkLoginAndCreateSession(login, password);
        if (session == null) {
            out.writeObject(ServerRequest.InvalidLoginOrPassword);
        } else {
            server.sessions.put(session.getKey(), session.getValue());
            out.writeObject(session.getKey());
        }
    }
}
