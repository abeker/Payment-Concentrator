package com.sep.Eureka.repository;

import com.sep.Eureka.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPaymentTypeRepository extends JpaRepository<PaymentType, UUID> {

    PaymentType findByPaymentType(String paymentType);

}
