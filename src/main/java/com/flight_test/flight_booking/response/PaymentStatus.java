package com.flight_test.flight_booking.response;

public class PaymentStatus {
    private String message;
    public PaymentStatus(){}
    public PaymentStatus(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

