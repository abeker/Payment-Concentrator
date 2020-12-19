package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAccountRepository extends JpaRepository<Account, UUID> {

    Account findByAccountNumber(String accountNumber);

}
