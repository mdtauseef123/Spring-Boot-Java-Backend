package com.flight_test.flight_booking.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class FlightSearchResponse {
    private int scheduleId;
    private String flightNumber;

    private String flightName;

    private String source;
    private String destination;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private Double EC_Price;
    private Double BC_Price;
    private Double FC_Price;
    private String path = "";

    private int EC_Seats;

    private int BC_Seats;

    private int FC_Seats;
    public FlightSearchResponse(){}

    public FlightSearchResponse(int scheduleId, String flightNumber, String flightName, String source, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, Double EC_Price, Double BC_Price, Double FC_Price, String path, int EC_Seats, int BC_Seats, int FC_Seats) {
        this.scheduleId = scheduleId;
        this.flightNumber = flightNumber;
        this.flightName = flightName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.EC_Price = EC_Price;
        this.BC_Price = BC_Price;
        this.FC_Price = FC_Price;
        this.path = path;
        this.EC_Seats = EC_Seats;
        this.BC_Seats = BC_Seats;
        this.FC_Seats = FC_Seats;
    }


    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Double getEC_Price() {
        return EC_Price;
    }

    public void setEC_Price(Double EC_Price) {
        this.EC_Price = EC_Price;
    }

    public Double getBC_Price() {
        return BC_Price;
    }

    public void setBC_Price(Double BC_Price) {
        this.BC_Price = BC_Price;
    }

    public Double getFC_Price() {
        return FC_Price;
    }

    public void setFC_Price(Double FC_Price) {
        this.FC_Price = FC_Price;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getEC_Seats() {
        return EC_Seats;
    }

    public void setEC_Seats(int EC_Seats) {
        this.EC_Seats = EC_Seats;
    }

    public int getBC_Seats() {
        return BC_Seats;
    }

    public void setBC_Seats(int BC_Seats) {
        this.BC_Seats = BC_Seats;
    }

    public int getFC_Seats() {
        return FC_Seats;
    }

    public void setFC_Seats(int FC_Seats) {
        this.FC_Seats = FC_Seats;
    }
}
