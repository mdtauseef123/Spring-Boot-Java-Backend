package com.flight_test.flight_booking.service;

import com.flight_test.flight_booking.entity.BookingDetails;
import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.entity.User;

import java.util.List;

public interface UserDetailsService {
    public User addUser(User user);

    public void updateUser(User user);

    public List<FlightSchedule> getFlightReports();

    public List<BookingDetails> getBookingDetails(int id);

    public User getUserById(int id);

    FlightSchedule getFlightScheduleByScheduleId(int id);

    List<BookingDetails> getAllBookings();
    public User loginUser(String username, String password);
}
