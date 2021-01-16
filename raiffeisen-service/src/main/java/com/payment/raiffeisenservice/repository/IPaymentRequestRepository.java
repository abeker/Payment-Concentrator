package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRequestRepository extends JpaRepository<PaymentRequest, Integer> {
}
