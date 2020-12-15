package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentRequestRepository extends JpaRepository<PaymentRequest, Integer> {
}
