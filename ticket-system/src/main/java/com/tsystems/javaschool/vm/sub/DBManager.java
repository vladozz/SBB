package com.tsystems.javaschool.vm.sub;

import com.tsystems.javaschool.vm.Passenger;
import com.tsystems.javaschool.vm.Path;
import com.tsystems.javaschool.vm.Station;
import com.tsystems.javaschool.vm.Train;

import javax.persistence.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DBManager {
    EntityManagerFactory emf;
    EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public DBManager() throws FileNotFoundException {
        emf = Persistence.createEntityManagerFactory("SBBPU");
        em = emf.createEntityManager();

        }

    public void closeEntities() {
        em.close();
        emf.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        DBManager manager = new DBManager();
        //System.out.println(manager.getEntityManager().find(Path.class, 2).getStations());
        //manager.addTrains();

        manager.closeEntities();
    }


    private void addPassenger(String firstName, String lastName, Date birthDate) {
        persist(new Passenger(firstName, lastName, birthDate));
    }


    private void addTrain(int id, int placesQty) {
        persist(new Train(id, (short) placesQty));
    }

    private void addStation(String title, String gmt) {
        persist(new Station(title, gmt));
    }

    private void joinStationsToPath(int id, int[] arr) {
        Path path = em.find(Path.class, id);
        List<Station> list = path.getStations();
        if (list == null) {
            list = new ArrayList<Station>();
            path.setStations(list);
        }
        List<Station> allStations = new ArrayList<>();
        allStations.add(new Station());
        allStations.addAll(getAllStations());
        for (Integer i : arr) {
            list.add(allStations.get(i));
        }

        merge(path);
    }

    private void remove(Object object) {
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            em.remove(object);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }
    }

    private void persist(Object object) {
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            em.persist(object);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }
    }

    private void merge(Object object) {
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            em.merge(object);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }
    }

    public void addAll() throws FileNotFoundException {
        addStations();
        addPaths();
        linkPathAndStations();
        addTrains();
    }

    private void linkPathAndStations() {
        int [][] array = {{1, 2, 4, 3, 10, 11}, {1, 5, 6, 13, 12},{2, 14, 6, 7}, {4, 2, 4, 3, 10, 11} };
        for (int i = 0; i < 4; i++) {
            joinStationsToPath(i * 2 + 1, array[i]);
            joinStationsToPath(i * 2 + 2, Hasher.reverseArray(array[i]));
        }
    }

    public void addPassengers() {
        addPassenger("Vladimir", "Putin", new Date());
        addPassenger("Dmitriy", "Medvedev", new Date());
        addPassenger("German", "Gref", new Date());
        addPassenger("Barak", "Obama", new Date());
        addPassenger("Sid", "Vicious", new Date());
        addPassenger("Ozzy", "Osbourne", new Date());
        addPassenger("Brad", "Pitt", new Date());
        addPassenger("Hannibal", "Lektor", new Date());
    }

    public List<Passenger> getAllPassengers()
    {
        String queryString = "SELECT s FROM Passenger s";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    public void addTrains() {
        addTrain(11, 60);
        addTrain(12, 120);
        addTrain(13, 120);
        addTrain(14, 60);
    }

    public List<Train> getAllTrains()
    {
        String queryString = "SELECT s FROM Train s";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    public void addStations() throws FileNotFoundException {
        List<Station> stations = new ArrayList<Station>();
        Scanner in = new Scanner(new FileReader("stations.txt"));

        while (in.hasNextLine()) {
            Station station = new Station();
            station.setTitle(in.nextLine());
            stations.add(station);
        }

        System.out.println(stations);

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
        }
    }

    public List<Station> getAllStations()
    {
        String queryString = "SELECT s FROM Station s";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    public void addPaths() throws FileNotFoundException {
        List<Path> paths = new ArrayList<Path>();
        Scanner in = new Scanner(new FileReader("paths.txt"));

        while (in.hasNextLine()) {
            Path path = new Path();
            path.setTitle(in.nextLine());
            paths.add(path);
        }

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
        }
    }

    public List<Path> getAllPaths()
    {
        String queryString = "SELECT p FROM Path p";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    public List getAllObjectsFromTable(Class T)
    {
        String queryString = "SELECT o FROM " + T.getCanonicalName() + " o";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }
}
