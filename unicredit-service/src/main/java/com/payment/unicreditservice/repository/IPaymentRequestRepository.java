package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRequestRepository extends JpaRepository<PaymentRequest, Integer> {
}
