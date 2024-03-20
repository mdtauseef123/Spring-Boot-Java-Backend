package com.flight_test.flight_booking.dao;

import com.flight_test.flight_booking.entity.BookingDetails;
import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User addUser(User user) {
        User newUser = entityManager.merge(user);
        return newUser;
    }

    @Override
    public void updateUser(User user) {
                entityManager.merge(user);
    }

    @Override
    public List<BookingDetails> getBookingDetails(int scheduleId) {
        try {
            Query query = entityManager.createQuery("SELECT bd FROM BookingDetails bd WHERE bd.scheduleId = :scheduleId", BookingDetails.class);
            query.setParameter("scheduleId", scheduleId);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace(); 
            return null;
        }
    }

    @Override
    public User getUserById(int id){
        return entityManager.find(User.class, id);
    }

    @Override
    public FlightSchedule getFlightScheduleByScheduleId(int id){
        return entityManager.find(FlightSchedule.class , id);
    };

    @Override
    public List<BookingDetails> getAllBookings(){
        return entityManager.createQuery("SELECT b FROM BookingDetails b", BookingDetails.class)
                .getResultList();
    }

    @Override
    public User loginUser(String username, String password) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
        Query query = entityManager.createQuery(jpql, User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        User loggedInUser;
        try {
            loggedInUser = (User) query.getSingleResult();
        } catch (NoResultException e) {
            loggedInUser = new User(0, "failed", "failed", "failed", "failed");
        }
        return loggedInUser;
    }

    @Override
    public List<FlightSchedule> getFlightReports() {
        Query query = entityManager.createQuery("SELECT fs FROM FlightSchedule fs", FlightSchedule.class);
        List<FlightSchedule> flightSchedules = query.getResultList();
        for (FlightSchedule flightSchedule : flightSchedules) {
            int economyClassSeatsBooked = getSeatsBooked(flightSchedule.getScheduleId(), "economy");
            int businessClassSeatsBooked = getSeatsBooked(flightSchedule.getScheduleId(), "business");
            int firstClassSeatsBooked = getSeatsBooked(flightSchedule.getScheduleId(), "first");

            int availableEconomyClassSeats = flightSchedule.getEC_Seats() - economyClassSeatsBooked;
            int availableBusinessClassSeats = flightSchedule.getBC_Seats() - businessClassSeatsBooked;
            int availableFirstClassSeats = flightSchedule.getFC_Seats() - firstClassSeatsBooked;

            flightSchedule.setEC_Seats(availableEconomyClassSeats);
            flightSchedule.setBC_Seats(availableBusinessClassSeats);
            flightSchedule.setFC_Seats(availableFirstClassSeats);
//            entityManager.persist(flightSchedule);
        }

        return flightSchedules;
    }

    private int getSeatsBooked(int scheduleId, String seatPreference) {
        Query query = entityManager.createQuery("SELECT COUNT(b) FROM BookingDetails b WHERE b.scheduleId = :scheduleId AND b.seatPreference = :seatPreference", Long.class);
        query.setParameter("scheduleId", scheduleId);
        query.setParameter("seatPreference", seatPreference);
        Long seatsBooked = (Long) query.getSingleResult();
        return seatsBooked != null ? seatsBooked.intValue() : 0;
    }
}
