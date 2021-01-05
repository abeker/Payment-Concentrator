package com.payment.bankservice.repository;

import com.payment.bankservice.entity.OrderCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderCounterRepository extends JpaRepository<OrderCounter, Integer> {
}
