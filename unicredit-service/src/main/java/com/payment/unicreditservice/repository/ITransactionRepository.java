package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, UUID> {
}
