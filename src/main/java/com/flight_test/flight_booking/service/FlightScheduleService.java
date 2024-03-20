package com.flight_test.flight_booking.service;

import com.flight_test.flight_booking.request.SearchClass;
import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.response.FlightSearchResponse;

import java.util.List;

public interface FlightScheduleService {

    public FlightSchedule getSchedulesByFlightId(int id);
    public FlightSchedule addFlightSchedule(FlightSchedule flightSchedule);

    public void deleteSchedule(int id);

    List<FlightSearchResponse> search(SearchClass s);
    public List<FlightSchedule> getAllFlight();




}
