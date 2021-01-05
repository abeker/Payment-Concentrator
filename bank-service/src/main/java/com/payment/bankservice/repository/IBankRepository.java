package com.payment.bankservice.repository;

import com.payment.bankservice.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IBankRepository extends JpaRepository<Bank, UUID> {

    Bank findByBankCode(String bankCode);
    Bank findByName(String bankName);

}
