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

    public void addPaths() throws FileNotFoundException {
        List<Path> paths = new ArrayList<Path>();
        Scanner in = new Scanner(new FileReader("paths.txt"));

        while (in.hasNextLine()) {
            Path path = new Path();
            path.setTitle(in.nextLine());
            paths.add(path);
        }
        dbManager.persist(paths);

/*        EntityManager em = dbManager.getEntityManager();
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            for (Path path : paths) {
                em.persist(path);
            }
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }*/
    }

    public void addStations() throws FileNotFoundException {
        List<Station> stations = new ArrayList<Station>();
        Scanner in = new Scanner(new FileReader("stations.txt"));

        while (in.hasNextLine()) {
            Station station = new Station();
            station.setTitle(in.nextLine());
            stations.add(station);
        }

        dbManager.persist(stations);

/*        EntityManager em = dbManager.getEntityManager();
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            for (Station station : stations) {
                em.persist(station);
            }
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }*/
    }

    public void addTrains() {
        dbManager.addTrain(11, 60);
        dbManager.addTrain(12, 120);
        dbManager.addTrain(13, 120);
        dbManager.addTrain(14, 60);
    }

    private void linkPathAndStations() {
        int [][] array = {{1, 2, 4, 3, 10, 11}, {1, 5, 6, 13, 12},{2, 14, 6, 7}, {4, 2, 4, 3, 10, 11} };
        for (int i = 0; i < 4; i++) {
            dbManager.joinStationsToPath(i * 2 + 1, array[i]);
            dbManager.joinStationsToPath(i * 2 + 2, Hasher.reverseArray(array[i]));
        }
    }

    @Override
    public void close() throws IOException {
        dbManager.close();
    }
}
