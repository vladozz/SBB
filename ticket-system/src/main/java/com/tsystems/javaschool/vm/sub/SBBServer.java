package com.tsystems.javaschool.vm.sub;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SBBServer {
    public void startServer() {
        int port = 6574;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
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
}
