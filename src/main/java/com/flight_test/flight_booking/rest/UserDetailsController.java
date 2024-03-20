package com.flight_test.flight_booking.rest;

import com.flight_test.flight_booking.entity.BookingDetails;
import com.flight_test.flight_booking.entity.FlightSchedule;
import com.flight_test.flight_booking.entity.User;
import com.flight_test.flight_booking.request.LoginClass;
import com.flight_test.flight_booking.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
public class UserDetailsController {
    private UserDetailsService userDetailsService;

    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        user.setId(0);
        return userDetailsService.addUser(user);
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
         userDetailsService.updateUser(user);
         return new ResponseEntity<>("User Details Updated", HttpStatus.OK);
    }

    @GetMapping("/getBooking/{id}")
    public List<BookingDetails> getBookingDetails(@PathVariable int id){
        return userDetailsService.getBookingDetails(id);
    }
    @GetMapping("/flights")
    List<FlightSchedule> getFlightReports(){
        return userDetailsService.getFlightReports();
    }

    @GetMapping("/flightReport")
    public ResponseEntity<byte[]> downloadFlightSchedulesAsCsv() {
        List<FlightSchedule> flightSchedules = userDetailsService.getFlightReports();

        StringBuilder csvContent = new StringBuilder();
        csvContent.append("Flight Number,Flight Name,Source,Destination,Departure Time,Arrival Time,Economy Seats,Business Seats,First Seats\n");
        for (FlightSchedule flightSchedule : flightSchedules) {
            csvContent.append(String.format("%s,%s,%s,%s,%s,%s,%d,%d,%d\n",
                    flightSchedule.getFlightNumber(),
                    flightSchedule.getFlightName(),
                    flightSchedule.getSource(),
                    flightSchedule.getDestination(),
                    flightSchedule.getDepartureTime(),
                    flightSchedule.getArrivalTime(),
                    flightSchedule.getEC_Seats(),
                    flightSchedule.getBC_Seats(),
                    flightSchedule.getFC_Seats()));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "flight_schedules.csv");

        try {
            File csvFile = File.createTempFile("flight_schedules", ".csv");
            FileWriter fileWriter = new FileWriter(csvFile);
            fileWriter.write(csvContent.toString());
            fileWriter.close();
            byte[] csvBytes = org.apache.commons.io.FileUtils.readFileToByteArray(csvFile);
            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookingReport")
    public ResponseEntity<byte[]> downloadBookingDetailsAsCsv() {
        List<BookingDetails> bookings = userDetailsService.getAllBookings();
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("User Name,Flight Number,Flight Name,Source,Destination,Departure Time,Arrival Time,Passenger Name,Passenger Email,Passenger Phone,Passenger Gender,Passenger Age,Price,Seat Preference\n");
        for (BookingDetails booking : bookings) {
            User user = userDetailsService.getUserById(booking.getUserId());
            String userName = user.getName();

            FlightSchedule flightSchedule = userDetailsService.getFlightScheduleByScheduleId(booking.getScheduleId()); ;
            String flightNumber = flightSchedule.getFlightNumber();
            String flightName = flightSchedule.getFlightName();
            String source = flightSchedule.getSource();
            String destination = flightSchedule.getDestination();
            LocalDateTime departureTime = flightSchedule.getDepartureTime();
            LocalDateTime arrivalTime = flightSchedule.getArrivalTime();
            String passengerName = booking.getPassengerName();
            String passengerEmail = booking.getPassengerEmail();
            String passengerPhone = booking.getPassengerPhone();
            String passengerGender = booking.getPassengerGender();
            int passengerAge = booking.getPassengerAge();
            double price = booking.getTicketPrice();
            String seatPreference = booking.getSeatPreference();
            csvContent.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%d,%.2f,%s\n",
                    userName,
                    flightNumber,
                    flightName,
                    source,
                    destination,
                    departureTime,
                    arrivalTime,
                    passengerName,
                    passengerEmail,
                    passengerPhone,
                    passengerGender,
                    passengerAge,
                    price,
                    seatPreference));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "booking_details.csv");
        try {
            File csvFile = File.createTempFile("booking_details", ".csv");
            FileWriter fileWriter = new FileWriter(csvFile);
            fileWriter.write(csvContent.toString());
            fileWriter.close();
            byte[] csvBytes = org.apache.commons.io.FileUtils.readFileToByteArray(csvFile);
            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/viewBooking")
//    public List<BookingDetails> viewBookings(){
//        return userDetailsService.getAllBookings();
//    }

    @PostMapping("/login")
    User logInUser(@RequestBody LoginClass theUser){
        return userDetailsService.loginUser(theUser.getUsername(), theUser.getPassword());
    }
}
