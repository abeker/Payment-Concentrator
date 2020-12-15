package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.OrderCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderCounterRepository extends JpaRepository<OrderCounter, Integer> {
}
