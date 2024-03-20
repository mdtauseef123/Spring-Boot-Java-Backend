package com.flight_test.flight_booking.dao;

import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.request.SearchClass;
import com.flight_test.flight_booking.response.FlightSearchResponse;

import java.util.List;

public interface FlightScheduleDAO {
    public FlightSchedule getSchedulesByFlightId(int id);

    public void deleteSchedule(int scheduleId);

    public FlightSchedule addFlightSchedule(FlightSchedule flightSchedule);

    List<FlightSearchResponse> searchFlights(SearchClass flightSchedule);

    public List<FlightSchedule> getAllFlight();


}
