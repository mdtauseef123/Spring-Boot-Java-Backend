package com.flight_test.flight_booking.dao;

import com.flight_test.flight_booking.entity.BookingDetails;
import com.flight_test.flight_booking.response.BookingDetailsWithUserAndFlight;

import java.util.List;

public interface BookingDetailsDAO {
    public BookingDetails deleteBooking(int id);

    public List<BookingDetailsWithUserAndFlight> getUpcomingBookings(int id);

    public List<BookingDetailsWithUserAndFlight> getPastBookings(int id);

    public List<BookingDetailsWithUserAndFlight> getCompleteBookings();

    public List<BookingDetails> addMultipleBookings(List<BookingDetails> bookingDetailsList);

}
