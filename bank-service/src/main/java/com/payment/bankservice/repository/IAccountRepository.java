package com.payment.bankservice.repository;

import com.payment.bankservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAccountRepository extends JpaRepository<Account, UUID> {

    Account findByAccountNumber(String accountNumber);

}
