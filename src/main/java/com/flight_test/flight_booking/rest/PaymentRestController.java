package com.flight_test.flight_booking.rest;

import com.flight_test.flight_booking.entity.Payment;
import com.flight_test.flight_booking.response.PaymentStatus;
import com.flight_test.flight_booking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentRestController {
    private PaymentService paymentService;
    @Autowired
    public PaymentRestController(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    @PostMapping("/payments")
    @CrossOrigin(origins = "http://localhost:4200")
    PaymentStatus makePayment(@RequestBody Payment paymentClass){
        return paymentService.makePayment(paymentClass.getPaymentData(), paymentClass.getAmount());
    }
}
