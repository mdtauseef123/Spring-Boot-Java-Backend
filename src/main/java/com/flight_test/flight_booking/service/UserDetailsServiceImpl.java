package com.flight_test.flight_booking.service;

import com.flight_test.flight_booking.dao.UserDAO;
import com.flight_test.flight_booking.entity.BookingDetails;
import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private UserDAO userDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User addUser(User user) {
        return userDAO.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
         userDAO.updateUser(user);
    }


    @Override
    public List<BookingDetails> getBookingDetails(int id) {
        return userDAO.getBookingDetails(id);
    }

    @Override
    public User getUserById(int id){
        return userDAO.getUserById(id);
    };

    @Override
    public FlightSchedule getFlightScheduleByScheduleId(int id){
        return userDAO.getFlightScheduleByScheduleId(id);
    };

    @Override
    public List<BookingDetails> getAllBookings(){
        return userDAO.getAllBookings();
    }

    @Override
    public User loginUser(String username, String password) {
        return userDAO.loginUser(username, password);
    }
    @Override
    public List<FlightSchedule> getFlightReports() {
        return userDAO.getFlightReports();
    }



}
