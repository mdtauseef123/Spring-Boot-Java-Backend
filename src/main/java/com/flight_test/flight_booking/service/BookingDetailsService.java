package com.flight_test.flight_booking.service;

import com.flight_test.flight_booking.entity.BookingDetails;
import com.flight_test.flight_booking.response.BookingDetailsWithUserAndFlight;

import java.util.List;

public interface BookingDetailsService {
   // public BookingDetails addBooking(BookingDetails bookingDetails);

    public BookingDetails deleteBooking(int id);

    public List<BookingDetailsWithUserAndFlight> getUpcomingBookings(int id);

    public List<BookingDetailsWithUserAndFlight> getPastBookings(int userId);


    public List<BookingDetailsWithUserAndFlight> getCompleteBookings();

    public List<BookingDetails> addMultipleBookings(List<BookingDetails> bookingDetailsList);


}
