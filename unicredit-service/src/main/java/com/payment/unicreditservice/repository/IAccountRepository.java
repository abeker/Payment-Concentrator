package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAccountRepository extends JpaRepository<Account, UUID> {
}
