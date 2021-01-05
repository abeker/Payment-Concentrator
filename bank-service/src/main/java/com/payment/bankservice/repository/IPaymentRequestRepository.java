package com.payment.bankservice.repository;

import com.payment.bankservice.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaymentRequestRepository extends JpaRepository<PaymentRequest, Integer> {

    List<PaymentRequest> findByPaymentCounter(int counter);

}
