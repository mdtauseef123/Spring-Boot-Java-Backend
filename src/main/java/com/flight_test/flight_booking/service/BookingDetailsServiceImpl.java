package com.flight_test.flight_booking.service;

import com.flight_test.flight_booking.dao.BookingDetailsDAO;
import com.flight_test.flight_booking.entity.BookingDetails;
import com.flight_test.flight_booking.response.BookingDetailsWithUserAndFlight;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BookingDetailsServiceImpl implements BookingDetailsService{

    private BookingDetailsDAO bookingDetailsDAO;

    @Autowired
    public BookingDetailsServiceImpl(BookingDetailsDAO bookingDetailsDAO) {
        this.bookingDetailsDAO = bookingDetailsDAO;
    }

//    @Override
//    @Transactional
//    public BookingDetails addBooking(BookingDetails bookingDetails) {
//        return bookingDetailsDAO.addBooking(bookingDetails);
//    }

    @Override
    @Transactional
    public BookingDetails deleteBooking(int id) {
        return bookingDetailsDAO.deleteBooking(id);
    }

    @Override
    public List<BookingDetailsWithUserAndFlight> getUpcomingBookings(int id) {
        return bookingDetailsDAO.getUpcomingBookings(id);
    }

    @Override
    public List<BookingDetailsWithUserAndFlight> getPastBookings(int userId){
        return bookingDetailsDAO.getPastBookings(userId);
    }

    @Override
    public List<BookingDetailsWithUserAndFlight> getCompleteBookings(){
        return bookingDetailsDAO.getCompleteBookings();
    };

    @Override
    @Transactional
    public List<BookingDetails> addMultipleBookings(List<BookingDetails> bookingDetailsList){
        return bookingDetailsDAO.addMultipleBookings(bookingDetailsList);
    }
}
