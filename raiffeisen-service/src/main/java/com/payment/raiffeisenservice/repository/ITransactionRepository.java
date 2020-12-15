package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITransactionRepository extends JpaRepository<Transaction, UUID> {
}
