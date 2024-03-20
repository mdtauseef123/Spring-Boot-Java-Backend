package com.flight_test.flight_booking.service;

import com.flight_test.flight_booking.dao.FlightScheduleDAO;
import com.flight_test.flight_booking.request.SearchClass;
import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.response.FlightSearchResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightScheduleServiceImpl implements FlightScheduleService{
    private FlightScheduleDAO flightScheduleDAO;
    @Autowired
    public FlightScheduleServiceImpl(FlightScheduleDAO flightScheduleDAO) {
        this.flightScheduleDAO = flightScheduleDAO;
    }

    @Override
    public FlightSchedule getSchedulesByFlightId(int id) {
        return flightScheduleDAO.getSchedulesByFlightId(id);
    }

    @Override
    @Transactional
    public FlightSchedule addFlightSchedule(FlightSchedule flightSchedule) {
        return flightScheduleDAO.addFlightSchedule(flightSchedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(int id) {
        flightScheduleDAO.deleteSchedule(id);
    }

    @Override
    public List<FlightSearchResponse> search(SearchClass s) {
        return flightScheduleDAO.searchFlights(s);
    }

    @Override
    public List<FlightSchedule> getAllFlight() {
        return flightScheduleDAO.getAllFlight();
    }

}
