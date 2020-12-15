package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITransactionRepository extends JpaRepository<Transaction, UUID> {
}
