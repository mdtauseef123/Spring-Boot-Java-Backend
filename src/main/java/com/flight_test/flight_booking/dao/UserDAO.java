package com.flight_test.flight_booking.dao;

import com.flight_test.flight_booking.entity.BookingDetails;
import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.entity.User;

import java.util.List;

public interface UserDAO {
    User addUser(User user);

    void updateUser(User user);

    List<FlightSchedule> getFlightReports();

    List<BookingDetails> getBookingDetails(int id);

    User getUserById(int id);

    FlightSchedule getFlightScheduleByScheduleId(int id);

    List<BookingDetails> getAllBookings();
    User loginUser(String username, String password);
}
