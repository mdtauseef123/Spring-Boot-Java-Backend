package com.flight_test.flight_booking.rest;

import com.flight_test.flight_booking.entity.BookingDetails;
import com.flight_test.flight_booking.response.BookingDetailsWithUserAndFlight;
import com.flight_test.flight_booking.service.BookingDetailsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BookingDetailsController {
    private BookingDetailsService bookingDetailsService;
    private EntityManager entityManager;

    @Autowired
    public BookingDetailsController(BookingDetailsService bookingDetailsService, EntityManager entityManager) {
        this.bookingDetailsService = bookingDetailsService;
        this.entityManager = entityManager;
    }

    @PostMapping("/booking")
    public List<BookingDetails> addMultipleBookings(@RequestBody  List<BookingDetails> bookingDetailsList){
        List<BookingDetails> ans = bookingDetailsService.addMultipleBookings(bookingDetailsList);
        return ans;
    }
//    @PostMapping("/booking")
//    public BookingDetails addBooking(@RequestBody BookingDetails bookingDetails) {
////        bookingDetails.setBookingId(0);
//        bookingDetails.setBookingDate(LocalDateTime.now());
//        BookingDetails newBookingDetails = bookingDetailsService.addBooking(bookingDetails);
//        return newBookingDetails;
//    }

    @DeleteMapping("/bookings/{id}")
    public BookingDetails deleteBooking(@PathVariable int id) {
        return bookingDetailsService.deleteBooking(id);
    }


    @GetMapping("/upcomingBookings/{id}")
    public List<BookingDetailsWithUserAndFlight> getUpcomingBookings(@PathVariable int id){
        return bookingDetailsService.getUpcomingBookings(id);
    }

    @GetMapping("/totalSeats/{scheduleId}")
    public int getSeatsLeft(@PathVariable int scheduleId){
        try {
            Query query = entityManager.createQuery("SELECT fs.BC_Seats + fs.FC_Seats + fs.EC_Seats " +
                    "FROM FlightSchedule fs WHERE fs.scheduleId = :scheduleId");
            query.setParameter("scheduleId", scheduleId);
            int totalSeats = (int) query.getSingleResult();

            Query bookingQuery = entityManager.createQuery("SELECT COUNT(b.scheduleId) " +
                    "FROM BookingDetails b WHERE b.scheduleId = :scheduleId");
            bookingQuery.setParameter("scheduleId", scheduleId);

            int totalBookings = ((Number) bookingQuery.getSingleResult()).intValue();
            int seatsLeft = totalSeats - totalBookings;

            return seatsLeft;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }

    }

    @GetMapping("/pastBookings/{userId}")
    public List<BookingDetailsWithUserAndFlight> getPastBookings(@PathVariable int userId){
        return bookingDetailsService.getPastBookings(userId);
    }

    @GetMapping("/bookings")
    public List<BookingDetailsWithUserAndFlight> getCompleteBookings(){
        return  bookingDetailsService.getCompleteBookings();
    };

}
