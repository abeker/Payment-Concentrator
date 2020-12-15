package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentRequestRepository extends JpaRepository<PaymentRequest, Integer> {
}
