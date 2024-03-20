package com.flight_test.flight_booking.dao;

import com.flight_test.flight_booking.response.PaymentStatus;

public interface PaymentDAO {
    PaymentStatus makePayment(String paymentData, int amount);
}
