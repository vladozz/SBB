package com.tsystems.javaschool.vm.sub;

import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


/**
 * Вспомогательный класс для тестового заполнения БД
 */
public class DBFillHelper implements Closeable {
    DBManager dbManager;

    public static void main(String[] args) throws FileNotFoundException {
        DBFillHelper dbFillHelper = new DBFillHelper();
        dbFillHelper.addAll();
    }

    public DBFillHelper() {
        dbManager = new DBManager();
    }

    public void addAll() throws FileNotFoundException {
        addStations();
        addPaths();
        linkPathAndStations();
        addTrains();
        addPassengers();
    }

    public void addPassengers() {
        dbManager.addPassenger("Vladimir", "Putin", new Date());
        dbManager.addPassenger("Dmitriy", "Medvedev", new Date());
        dbManager.addPassenger("German", "Gref", new Date());
        dbManager.addPassenger("Barak", "Obama", new Date());
        dbManager.addPassenger("Sid", "Vicious", new Date());
        dbManager.addPassenger("Ozzy", "Osbourne", new Date());
        dbManager.addPassenger("Brad", "Pitt", new Date());
        dbManager.addPassenger("Hannibal", "Lektor", new Date());
    }








    @Override
    public void close() throws IOException {
        dbManager.close();
    }
}
