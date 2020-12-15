package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.MerchantOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMerchantOrderRepository extends JpaRepository<MerchantOrder, Integer> {
}
