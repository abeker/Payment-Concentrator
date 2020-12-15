package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAccountRepository extends JpaRepository<Account, UUID> {
}
