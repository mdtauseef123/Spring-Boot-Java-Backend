package com.flight_test.flight_booking.dao;

import com.flight_test.flight_booking.entity.Payment;
import com.flight_test.flight_booking.response.PaymentStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentDAOImpl implements PaymentDAO{
    private EntityManager entityManager;

    @Autowired
    public PaymentDAOImpl(EntityManager theEntityManager){
        this.entityManager = theEntityManager;
    }
    @Override
    public PaymentStatus makePayment(String paymentData, int amount) {
        String jpql = "SELECT f FROM Payment f WHERE f.paymentData = :paymentData AND f.amount >= :amount";
        TypedQuery<Payment> query = entityManager.createQuery(jpql, Payment.class);
        query.setParameter("paymentData", paymentData);
        query.setParameter("amount", amount);
        List<Payment> res = query.getResultList();
        if(res.size() != 0){
            return new PaymentStatus("Payment Successful");
        } else {
            return new PaymentStatus("Payment Failed");
        }
    }
}

