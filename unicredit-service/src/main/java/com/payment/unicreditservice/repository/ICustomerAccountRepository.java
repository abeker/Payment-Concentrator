package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICustomerAccountRepository extends JpaRepository<CustomerAccount, UUID> {
}
