package com.flight_test.flight_booking.dao;

import com.flight_test.flight_booking.entity.BookingDetails;
import com.flight_test.flight_booking.response.BookingDetailsWithUserAndFlight;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingDetailsDAOImpl implements BookingDetailsDAO {

    private EntityManager entityManager;

    @Autowired
    public BookingDetailsDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<BookingDetails> addMultipleBookings(List<BookingDetails> bookingDetailsList) {
        List<BookingDetails> savedBookingDetails = new ArrayList<>();
        for (BookingDetails bookingDetails : bookingDetailsList) {
            bookingDetails.setBookingDate(LocalDateTime.now());
            BookingDetails savedDetail = entityManager.merge(bookingDetails);
            savedBookingDetails.add(savedDetail);
        }
        return savedBookingDetails;
    }

    @Override
    public BookingDetails deleteBooking(int id) {
        BookingDetails tmp = entityManager.find(BookingDetails.class, id);
        entityManager.remove(tmp);
        return tmp;
    }

    @Override
    public List<BookingDetailsWithUserAndFlight> getUpcomingBookings(int userId)  {
        LocalDateTime today = LocalDateTime.now();
        return entityManager.createQuery(
                        "SELECT new com.flight_test.flight_booking.response.BookingDetailsWithUserAndFlight(" +
                                "b.bookingId, b.userId, b.passengerName, b.passengerEmail, b.passengerPhone, " +
                                "b.passengerGender, b.passengerAge, b.ticketPrice, b.seatPreference, b.bookingDate, " +
                                "b.scheduleId, fs.flightName, fs.flightNumber, u.name, fs.source , fs.destination , fs.departureTime , fs.arrivalTime) " +
                                "FROM BookingDetails b " +
                                "JOIN FlightSchedule fs ON b.scheduleId = fs.scheduleId " +
                                "JOIN User u ON b.userId = u.id " +
                                "WHERE b.userId = :user_Id " +
                                "AND fs.departureTime >= :today", BookingDetailsWithUserAndFlight.class)
                .setParameter("user_Id", userId)
                .setParameter("today", today)
                .getResultList();
    }

    @Override
    public List<BookingDetailsWithUserAndFlight> getPastBookings(int userId) {
        LocalDateTime today = LocalDateTime.now();
        return entityManager.createQuery(
                        "SELECT new com.flight_test.flight_booking.response.BookingDetailsWithUserAndFlight(" +
                                "b.bookingId, b.userId, b.passengerName, b.passengerEmail, b.passengerPhone, " +
                                "b.passengerGender, b.passengerAge, b.ticketPrice, b.seatPreference, b.bookingDate, " +
                                "b.scheduleId, fs.flightName, fs.flightNumber, u.name, fs.source , fs.destination , fs.departureTime , fs.arrivalTime) " +
                                "FROM BookingDetails b " +
                                "JOIN FlightSchedule fs ON b.scheduleId = fs.scheduleId " +
                                "JOIN User u ON b.userId = u.id " +
                                "WHERE b.userId = :user_Id " +
                                "AND fs.departureTime < :today", BookingDetailsWithUserAndFlight.class)
                .setParameter("user_Id", userId)
                .setParameter("today", today)
                .getResultList();
    }

    @Override
    public List<BookingDetailsWithUserAndFlight> getCompleteBookings(){
        return entityManager.createQuery(
                        "SELECT new com.flight_test.flight_booking.response.BookingDetailsWithUserAndFlight(" +
                                "b.bookingId, b.userId, b.passengerName, b.passengerEmail, b.passengerPhone, " +
                                "b.passengerGender, b.passengerAge, b.ticketPrice, b.seatPreference, b.bookingDate, " +
                                "b.scheduleId, fs.flightName, fs.flightNumber, u.name, fs.source , fs.destination , fs.departureTime , fs.arrivalTime) " +
                                "FROM BookingDetails b " +
                                "JOIN FlightSchedule fs ON b.scheduleId = fs.scheduleId " +
                                "JOIN User u ON b.userId = u.id", BookingDetailsWithUserAndFlight.class)
                .getResultList();
    }
}
