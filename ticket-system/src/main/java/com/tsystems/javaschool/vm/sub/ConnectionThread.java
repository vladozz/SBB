package com.tsystems.javaschool.vm.sub;

import java.net.Socket;

public class ConnectionThread implements Runnable {
    private Socket incoming;

    public ConnectionThread(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {

        //TODO: реализация
    }
}
