package com.payment.bankservice.repository;

import com.payment.bankservice.entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICustomerAccountRepository extends JpaRepository<CustomerAccount, UUID> {
}
