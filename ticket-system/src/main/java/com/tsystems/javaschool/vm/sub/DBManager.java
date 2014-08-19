package com.tsystems.javaschool.vm.sub;

import com.tsystems.javaschool.vm.domain.*;

import javax.persistence.*;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.*;

public class DBManager implements Closeable {
    EntityManagerFactory emf;
    EntityManager em;

    /**
     *
     * @return ссылка на EntityManager
     */
    public EntityManager getEntityManager() {
        return em;
    }

    public DBManager() {
        emf = Persistence.createEntityManagerFactory("SBBPU");
        em = emf.createEntityManager();
        }

    public static void main(String[] args) throws FileNotFoundException {
        DBManager manager = new DBManager();
        //System.out.println(manager.getEntityManager().find(Path.class, 2).getStations());
        //manager.addTrains();

        manager.close();
    }

    /**
     * Недописанный метод
     * @param path
     * @param train
     */
    public void generateTripAndAddBoard(Path path, Train train) {
        Trip trip = addTrip(path, train);
        //TODO: finish this method
    }

    /**
     * Создание расписание для рейса
     * @param trip рейс
     * @param arrives список времен прибытия
     * @param departures список времен отправления
     * @return список созданных строчек расписания
     */
    public List<Board> generateBoardByTrip(Trip trip, List<Timestamp> arrives, List<Timestamp> departures) {
        List<Board> board = new ArrayList<Board>();
        List<Station> stations = trip.getPath().getStations();
        if (stations.size() != arrives.size() || stations.size() != departures.size()) {
            //TODO: throw MyException: different sizes of arrays
        }
        int n = stations.size();
        for (int i = 0; i < n; i++) {
            Board boardLine = new Board(trip, stations.get(i), arrives.get(i), departures.get(i));
            board.add(boardLine);
        }
        persist(board);
        return board;
    }

    /**
     * Создание и объекта Passenger и добавление его в БД
     * @param firstName
     * @param lastName
     * @param birthDate
     * @return созданный объект
     */
    public Passenger addPassenger(String firstName, String lastName, Date birthDate) {
        Passenger newPassenger = new Passenger(firstName, lastName, birthDate);
        persist(newPassenger);
        return newPassenger;
    }

    /**
     * Создание и объекта Train и добавление его в БД
     * @param path
     * @param train
     * @return созданный объект
     */
    public Trip addTrip(Path path, Train train) {
        Trip newTrip = new Trip(path, train);
        persist(newTrip);
        return newTrip;
    }

    /**
     * Создание и объекта Train и добавление его в БД
     * @param id
     * @param placesQty
     * @return созданный объект
     */
    public Train addTrain(int id, int placesQty) {
        Train newTrain = new Train(id, (short) placesQty);
        persist(newTrain);
        return newTrain;
    }

    /**
     * Создание и объекта Station и добавление его в БД
     * @param title
     * @param timeZone
     * @return созданный объект
     */
    public Station addStation(String title, TimeZone timeZone) {

        Station newStation = new Station(title, timeZone);
        persist(newStation);
        return newStation;
    }


    /**
     * Метод, добавляющий станцию в маршрут (в середину или в конец)
     * @param path маршрут
     * @param station станция
     * @param index 0 - добавление в конец, от 1...n(кол-во станций в маршруте) - вставка на место 1го...n-го элемента
     *               со сдвигом всех последующих
     */
    public void addStationToPath(Path path, Station station, int index) throws Exception {
        List<Station> stations = path.getStations();
        if (index < 0 || index > stations.size()) {
            throw new Exception("Illegal index of station: " + "index = " + index
                    + "stations.size() = " + stations.size() + "path = " + path + "station = " + station);
            //TODO: possible change class of exception
        }
        if (index == 0) {
            stations.add(station);
        } else {
            stations.add(index - 1, station);
        }
    }

    /**
     *
     * @param path
     * @param station
     * @param index от 1...n(кол-во станций в маршруте) - удаление 1го...n-го элемента
     *               со сдвигом всех последующих
     */
    public void removeStationFromPath(Path path, Station station, int index) throws Exception {
        List<Station> stations = path.getStations();
        if (index <= 0 || index > stations.size()) {
            throw new Exception("Illegal index of station: " + "index = " + index
                    + "stations.size() = " + stations.size() + "path = " + path + "station = " + station);
            //TODO: possible change class of exception
        }
        stations.remove(index - 1);
    }

    /**
     * метод-обретка для объектов
     * @param object
     * @return успешность выполнения транзакции
     */
    public boolean remove(Object object) {
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            em.remove(object);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
                return false;
            }
            return true;
        }
    }

    /**
     * метод-обретка для коллекций объектов
     * @param collection
     * @return успешность выполнения транзакции
     */
    public boolean remove(Collection collection) {
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            for (Object object : collection) {
                em.remove(object);
            }
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
                return false;
            }
            return true;
        }
    }

    /**
     * метод-обретка для объектов
     * @param object
     * @return успешность выполнения транзакции
     */
    public boolean persist(Object object) {
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            em.persist(object);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
                return false;
            }
            return true;
        }
    }

    /**
     * метод-обретка для коллекций объектов
     * @param collection
     * @return успешность выполнения транзакции
     */
    public boolean persist(Collection collection) {
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            for (Object object : collection) {
                em.persist(object);
            }
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
                return false;
            }
            return true;
        }
    }

    /**
     * метод-обретка для объектов
     * @param object
     * @return успешность выполнения транзакции
     */
    public boolean merge(Object object) {
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            em.merge(object);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
                return false;
            }
            return true;
        }
    }

    /**
     * метод-обретка для коллекций объектов
     * @param collection
     * @return успешность выполнения транзакции
     */
    public boolean merge(Collection collection) {
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();
            for (Object object : collection) {
                em.merge(object);
            }
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
                return false;
            }
            return true;
        }
    }

    /**
     * Старый метод, добавляющий станции к маршруту
     * @param id - id станции в БД
     * @param arr - массив id станций в БД
     */
    public void joinStationsToPath(int id, int[] arr) {
        //TODO: refactor this method
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

    /**
     * Старый метод, добавляющий станции к маршруту
     * @param path - станция
     * @param stations - массив станций
     */
    public void joinStationsToPath(Path path, List<Station> stations) {
        //TODO: refactor this method
        List<Station> list = path.getStations();
        if (list == null) {
            list = new ArrayList<Station>();
            path.setStations(list);
        }
        for (Station station : stations) {
            list.add(station);
        }
        merge(path);
    }

    /**
     * Метод, возвращающий расписание для станции в интервале между after и before
     * @param station
     * @param before
     * @param after
     * @return
     */
    public List<Board> getBoardForStation(Station station, Timestamp before, Timestamp after) {
        String queryString = "SELECT b FROM Board b WHERE b.station = :station and b.departureTime >= :after and " +
                "b.arriveTime <= :before";
        Query query = em.createQuery(queryString);
        query.setParameter("station", station);
        query.setParameter("before", before);
        query.setParameter("after", after);
        return query.getResultList();
    }

    /**
     * Метод, возвращащий кол-во доступных для покупки мест на рейс между желаемыми станциями отправления и прибытия
     * @param departure
     * @param arrive
     * @return кол-во доступных для покупки мест
     */
    public int countFreePlacesOfTrip(Board departure, Board arrive) {
        Trip trip = departure.getTrip();
        Path path = trip.getPath();
        //TODO: поменять код, чтобы станции брались из расписания, а не маршрута
        List<Station> stations = path.getStations();
        List<Ticket> tickets = getTicketsOfTrip(trip);

        int begin = stations.indexOf(departure.getStation());
        int end = stations.indexOf(arrive.getStation());
        int max = 0;
        int[] fillness = new int[end - begin];
        for (int i = begin; i < end; i++) {
            int c = 0;
            for (Ticket t : tickets) {
                if (stations.indexOf(t.getDeparture().getStation()) <= i &&
                        stations.indexOf(t.getArrive().getStation()) >= i + 1) {
                    c++;
                }
            }
            fillness[i - begin] = c;
            if (c > max) {
                max = c;
            }
        }
        return trip.getTrain().getPlacesQty() - max;
    }

    /**
     * Метод, отвечающий, есть ли у пассажира билет на данный рейс
     * @param trip рейс
     * @param passenger пассажир
     * @return
     */

    public boolean isOnTrip(Trip trip, Passenger passenger) {
        List<Ticket> tickets = getTicketsOfTrip(trip);

        for (Ticket t : tickets) {
            if (t.getPassenger().equals(passenger)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод, отвечающий, можно ли пассажиру купить билет на определенный рейс от станции отправления до станции прибытия
     * @param departure
     * @param arrive
     * @param passenger
     * @return
     */

    public boolean canBuyTicket(Board departure, Board arrive, Passenger passenger) {
        final long TEN_MINUTES = 1000L * 60 * 10;
        if (countFreePlacesOfTrip(departure, arrive) <= 0) {
            return false;
        }
        if (isOnTrip(departure.getTrip(), passenger)) {
            return false;
        }
        if (departure.getDepartureTime().getTime() - (new Date()).getTime() < TEN_MINUTES ) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param trip - рейс
     * @return список всех пассажиров, купивших билет на рейс
     */

    public List<Passenger> getPassengersOfTrip(Trip trip) {
        String queryString = "SELECT DISTINCT p FROM Passenger p INNER JOIN p.tickets t WHERE t.arrive.trip = :trip";
        Query query = em.createQuery(queryString);
        query.setParameter("trip", trip);
        return query.getResultList();
    }

    /**
     *
     * @param trip - рейс
     * @return список всех купленных билетов на определенный рейс
     */
    public List<Ticket> getTicketsOfTrip(Trip trip) {
        String queryString = "SELECT DISTINCT t FROM Ticket t WHERE t.arrive.trip = :trip";
        Query query = em.createQuery(queryString);
        query.setParameter("trip", trip);
        return query.getResultList();
    }

    /**
     *
     * @return список всех пассажиров в БД
     */
    public List<Passenger> getAllPassengers()
    {
        String queryString = "SELECT s FROM Passenger s";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    /**
     *
     * @return список всех маршрутов в БД
     */
    public List<Path> getAllPaths()
    {
        String queryString = "SELECT p FROM Path p";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    /**
     *
     * @return список всех станций в БД
     */
    public List<Station> getAllStations()
    {
        String queryString = "SELECT s FROM Station s";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    /**
     *
     * @return список всех поездов в БД
     */
    public List<Train> getAllTrains()
    {
        String queryString = "SELECT s FROM Train s";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    /**
     *
     * @return список всех рейсов в БД
     */
    public List<Trip> getAllTrips() {
        String queryString = "SELECT s FROM Trip s";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    /**
     *
     * @param T - класс сущности, список которых требуется вернуть
     * @return список экземляров класса, передаваемого в параметре, в БД
     */
    public List getAllObjectsFromTable(Class T)
    {
        String queryString = "SELECT o FROM " + T.getCanonicalName() + " o";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    @Override
    public void close()  {
        em.close();
        emf.close();
    }
}
