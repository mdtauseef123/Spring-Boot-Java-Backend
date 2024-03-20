package com.flight_test.flight_booking.rest;

import com.flight_test.flight_booking.request.SearchClass;
import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.response.FlightSearchResponse;
import com.flight_test.flight_booking.service.FlightScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class FlightScheduleController {
    private FlightScheduleService flightScheduleService;
    @Autowired
    public FlightScheduleController(FlightScheduleService flightScheduleService) {
        this.flightScheduleService = flightScheduleService;
    }
    @PostMapping("/search")
    List<FlightSearchResponse> searchFlight(@RequestBody SearchClass flight){
        return flightScheduleService.search(flight);
    }
     //getALLFlight
    @GetMapping("/getAllFlight")
    List<FlightSchedule> getAllFlight(){
        return flightScheduleService.getAllFlight();
    }
    @PostMapping("/flights")
    public FlightSchedule addFlightSchedule(@RequestBody FlightSchedule flightSchedule) {
        flightSchedule.setScheduleId(0);
        FlightSchedule newFlightSchedule = flightScheduleService.addFlightSchedule(flightSchedule);
        return newFlightSchedule;
    }
    @PutMapping("/flights")
    public FlightSchedule updateSchedule(@RequestBody FlightSchedule flightSchedule) {
        return flightScheduleService.addFlightSchedule(flightSchedule);
    }
    @DeleteMapping("/flights/{id}")
    public void deleteSchedule(@PathVariable int id) {
         flightScheduleService.deleteSchedule(id);
    }
    @GetMapping("search/{id}")
    public FlightSchedule getFLightScheduleById(@PathVariable int id){
        return flightScheduleService.getSchedulesByFlightId(id);
    }
}
