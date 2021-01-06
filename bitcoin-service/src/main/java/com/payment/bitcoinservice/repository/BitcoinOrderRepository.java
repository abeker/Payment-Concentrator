package com.payment.bitcoinservice.repository;

import com.payment.bitcoinservice.model.BitcoinOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitcoinOrderRepository extends JpaRepository<BitcoinOrder,Long> {
    BitcoinOrder findByOrderId(String orderId);
}
