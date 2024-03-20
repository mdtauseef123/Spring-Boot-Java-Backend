package com.flight_test.flight_booking.service;


import com.flight_test.flight_booking.dao.PaymentDAO;
import com.flight_test.flight_booking.response.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{
    private PaymentDAO paymentDAO;
    @Autowired
    public PaymentServiceImpl(PaymentDAO paymentDAO){
        this.paymentDAO = paymentDAO;
    }
    @Override
    public PaymentStatus makePayment(String paymentData, int amount) {
        return paymentDAO.makePayment(paymentData, amount);
    }
}
