package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICustomerAccountRepository extends JpaRepository<CustomerAccount, UUID> {
}
