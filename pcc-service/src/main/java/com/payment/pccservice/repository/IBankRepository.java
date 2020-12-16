package com.payment.pccservice.repository;

import com.payment.pccservice.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IBankRepository extends JpaRepository<Bank, UUID> {

    Bank findByBankCode(String bankCode);

}
