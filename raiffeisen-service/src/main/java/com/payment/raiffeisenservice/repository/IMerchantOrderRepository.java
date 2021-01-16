package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.MerchantOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMerchantOrderRepository extends JpaRepository<MerchantOrder, Integer> {
}
