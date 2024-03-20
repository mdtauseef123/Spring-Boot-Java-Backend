package com.flight_test.flight_booking.dao;

import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.request.SearchClass;
import com.flight_test.flight_booking.response.FlightSearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class FlightScheduleDAOImpl implements FlightScheduleDAO{

    private EntityManager entityManager;

    public FlightScheduleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public FlightSchedule getSchedulesByFlightId(int id) {
        return entityManager.find(FlightSchedule.class , id);
    }

    @Override
    @Transactional
    public void deleteSchedule(int scheduleId) {
            try {
                entityManager.createQuery("DELETE FROM BookingDetails b WHERE b.scheduleId = :scheduleId")
                        .setParameter("scheduleId", scheduleId)
                        .executeUpdate();

                entityManager.createQuery("DELETE FROM FlightSchedule fs WHERE fs.scheduleId = :scheduleId")
                        .setParameter("scheduleId", scheduleId)
                        .executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete schedule with ID: " + scheduleId, e);
            }
    }
    @Override
    public FlightSchedule addFlightSchedule(FlightSchedule flightSchedule) {
        FlightSchedule newFlightSchedule = entityManager.merge(flightSchedule);
        return newFlightSchedule;
    }

    public void allPaths(Map<String, List<String>> adj, Map<String, Boolean> visited, List<List<String>> res, List<String> currPath, String u, String v) {
        if (visited.get(u)) {
            return;
        }
        visited.put(u, true);
        currPath.add(u);

        if (u.equals(v)) {
            List<String> path = new ArrayList<>(currPath);
            res.add(path);
            visited.put(u, false);
            currPath.remove(currPath.size() - 1);
            return;
        }
        for (String x : adj.get(u)) {
            allPaths(adj, visited, res, currPath, x, v);
        }
        currPath.remove(currPath.size() - 1);
        visited.put(u, false);
    }

    public void allPaths_2(Map<String, List<String>> adj, Map<String, Boolean> visited, List<List<String>> res, List<String> currPath, String u, String v) {
        if (visited.get(u) || adj.get(u) == null) {
            return;
        }
        visited.put(u, true);
        currPath.add(u);

        if (u.equals(v)) {
            List<String> path = new ArrayList<>(currPath);
            res.add(path);
            visited.put(u, false);
            currPath.remove(currPath.size() - 1);
            return;
        }
        for (String x : adj.get(u)) {
            allPaths_2(adj, visited, res, currPath, x, v);
        }
        currPath.remove(currPath.size() - 1);
        visited.put(u, false);
    }

    public List<FlightSearchResponse> searchFlightsSal(SearchClass s) {
        TypedQuery<FlightSchedule> t = entityManager.createQuery("from FlightSchedule", FlightSchedule.class);
        List<FlightSchedule> allFlights = t.getResultList();

        List<FlightSearchResponse> direct = new ArrayList<>();
//        for(FlightSchedule f : allFlights) {
//            if (f.getSource().equals(s.getSource()) &&
//                    f.getDestination().equals(s.getDestination()) &&
//                    !f.getDepartureTime().toLocalDate().isBefore(s.getDepartureDate())){
//                direct.add(new FlightSearchResponse());
//            }
//        }
//        List<FlightSchedule> oneStop = new ArrayList<>();
//        for (FlightSchedule p : allFlights){
//            for (FlightSchedule k : allFlights) {
//                if (!(p.equals(k)) && p.getSource().equals(s.getSource())
//                        && p.getDestination().equals(k.getSource()) &&
//                        k.getDestination().equals(s.getDestination()) &&
//                        !p.getDepartureTime().isBefore(s.getTime())){
//                    oneStop.add(p);
//                    oneStop.add(k);
//                }
//            }
//        }
//        System.out.println(oneStop);

        List<List<String>> map = new ArrayList<>();
        for (FlightSchedule f : allFlights) {
            map.add(Arrays.asList(f.getSource(), f.getDestination()));
        }
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (List<String> pair : map) {
            String destination = pair.get(0);
            String source = pair.get(1);

            hashMap.putIfAbsent(destination, new ArrayList<>());
            hashMap.get(destination).add(source);
        }

        Set<String> hashSet = new HashSet<>();

        for (FlightSchedule l : allFlights) {
            hashSet.add(l.getSource());
            hashSet.add(l.getDestination());
        }
        Map<String, Integer> inDegree = new HashMap<>();

        for(String string : hashSet){
            inDegree.put(string, 0);
        }

        for (List<String> o : map) {
            inDegree.put(o.get(1), inDegree.get(o.get(1)) + 1);
        }

        Map<String, Boolean> discovered = new HashMap<>();
        for (String g : hashSet) {
            discovered.put(g, false);
        }

        List<String> path = new ArrayList<>();

        List<List<String>> res = new ArrayList<>();

        List<String> currPath = new ArrayList<>();
        try {
            allPaths(hashMap, discovered, res, currPath, s.getSource(), s.getDestination());
        } catch (Exception e){
            res.clear();
            currPath.clear();
            allPaths_2(hashMap, discovered, res, currPath, s.getSource(), s.getDestination());
        }
        System.out.println(res);

        List<FlightSchedule> finalFlights = new ArrayList<>();
        LocalDate lastTime = s.getDepartureDate();
        List<List<FlightSchedule>> result = new ArrayList<>();

        for(List<String> output : res){
            if (output.size() == 2) {
                continue;
            }

            for (int i = 1; i < output.size(); i++) {
                String source = output.get(i - 1);
                String destination = output.get(i);

                for (FlightSchedule flight : allFlights) {
                    if (flight.getSource().equals(source) &&
                            flight.getDestination().equals(destination) &&
                            !flight.getDepartureTime().toLocalDate().isBefore(lastTime)) {
                        finalFlights.add(flight);

                        lastTime = flight.getArrivalTime().toLocalDate();
                        break;
                    }
                }
            }
            result.add(new ArrayList<>(finalFlights));
            finalFlights.clear();
            lastTime = s.getDepartureDate();
        }

        for (List<FlightSchedule> curr : result) {
            FlightSearchResponse connectionFlight = new FlightSearchResponse();
            connectionFlight.setScheduleId(curr.get(0).getScheduleId());
            connectionFlight.setFlightName(curr.get(0).getFlightName());
            connectionFlight.setFlightNumber(curr.get(0).getFlightNumber());
            connectionFlight.setSource(s.getSource());
            connectionFlight.setDestination(s.getDestination());
            connectionFlight.setDepartureTime(s.getDepartureDate().atStartOfDay());
            connectionFlight.setArrivalTime(curr.get(curr.size() - 1).getArrivalTime());
            connectionFlight.setEC_Seats(curr.get(0).getEC_Seats());
            connectionFlight.setFC_Seats(curr.get(0).getFC_Seats());
            connectionFlight.setBC_Seats(curr.get(0).getBC_Seats());
            connectionFlight.setBC_Price(0.00);
            connectionFlight.setFC_Price(0.00);
            connectionFlight.setEC_Price(0.00);
            connectionFlight.setPath(s.getSource());
            for (FlightSchedule b : curr) {
                connectionFlight.setBC_Price(connectionFlight.getBC_Price() + b.getBC_Price());
                connectionFlight.setFC_Price(connectionFlight.getFC_Price() + b.getFC_Price());
                connectionFlight.setEC_Price(connectionFlight.getEC_Price() + b.getEC_Price());
                connectionFlight.setPath(connectionFlight.getPath() + " -> " + b.getDestination());
            }
            direct.add(connectionFlight);
        }
        return direct;
    }

    @Override
    public List<FlightSearchResponse> searchFlights(SearchClass searchClass) {
        String jpql = "SELECT f FROM FlightSchedule f WHERE f.source = :from AND f.destination = :to " +
                "AND FUNCTION('DATE', f.departureTime) = :departureDate";
        String flightNumber = searchClass.getFlightNumber();
        LocalDate returningTime = searchClass.getReturningDate();
        if(flightNumber.length() != 0){
            jpql += " AND f.flightNumber = :flightNumber";
        }
        if(returningTime != null){
            jpql += " OR f.source = :to AND f.destination = :from AND FUNCTION('DATE', f.departureTime) = DATE(:returningDate)";
        }
        TypedQuery<FlightSchedule> query = entityManager.createQuery(jpql, FlightSchedule.class);
        query.setParameter("from", searchClass.getSource());
        query.setParameter("to", searchClass.getDestination());
        query.setParameter("departureDate", searchClass.getDepartureDate());
        //System.out.println(searchClass.getSource() + " " + searchClass.getDestination() + " " + searchClass.getDepartureDate());
        if(returningTime != null) {
            query.setParameter("returningDate", searchClass.getReturningDate());
        }
        if(flightNumber.length() != 0){
            query.setParameter("flightNumber", flightNumber);
        }
        List<FlightSchedule> res = query.getResultList();
        List<FlightSearchResponse> nonStop = new ArrayList<>();
        for(FlightSchedule eachFlight: res){
            FlightSearchResponse obj = new FlightSearchResponse();
            obj.setScheduleId(eachFlight.getScheduleId());
            obj.setFlightName(eachFlight.getFlightName());
            obj.setFlightNumber(eachFlight.getFlightNumber());
            obj.setSource(eachFlight.getSource());
            obj.setDestination(eachFlight.getDestination());
            obj.setDepartureTime(eachFlight.getDepartureTime());
            obj.setArrivalTime(eachFlight.getArrivalTime());
            obj.setEC_Seats(eachFlight.getEC_Seats());
            obj.setFC_Seats(eachFlight.getFC_Seats());
            obj.setBC_Seats(eachFlight.getBC_Seats());
            obj.setEC_Price(eachFlight.getEC_Price());
            obj.setFC_Price(eachFlight.getFC_Price());
            obj.setBC_Price(eachFlight.getBC_Price());
            obj.setPath("non-stop");
            nonStop.add(obj);
        }
        List<FlightSearchResponse> withStop = searchFlightsSal(searchClass);
        nonStop.addAll(withStop);
        return nonStop;
    }
    @Override
    public List<FlightSchedule> getAllFlight() {
        TypedQuery<FlightSchedule> allFlights = entityManager.createQuery("FROM FlightSchedule", FlightSchedule.class);
        return allFlights.getResultList();
    }
}
