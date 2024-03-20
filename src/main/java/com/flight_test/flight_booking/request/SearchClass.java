package com.flight_test.flight_booking.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SearchClass {
    private String source;
    private String destination;
    private LocalDate departureDate;
    private LocalDate returningDate;
    private String flightNumber;
    public SearchClass(){}

    public SearchClass(String source, String destination, LocalDate departureDate, LocalDate returningDate, String flightNumber) {
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.returningDate = returningDate;
        this.flightNumber = flightNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturningDate() {
        return returningDate;
    }

    public void setReturningDate(LocalDate returningDate) {
        this.returningDate = returningDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}