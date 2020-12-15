package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.OrderCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderCounterRepository extends JpaRepository<OrderCounter, Integer> {
}
