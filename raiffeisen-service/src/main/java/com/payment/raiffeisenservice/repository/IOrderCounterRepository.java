package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.OrderCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderCounterRepository extends JpaRepository<OrderCounter, Integer> {
}
