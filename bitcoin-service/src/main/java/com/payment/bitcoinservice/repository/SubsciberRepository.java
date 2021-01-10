package com.payment.bitcoinservice.repository;

import com.payment.bitcoinservice.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubsciberRepository extends JpaRepository<Subscriber, Long> {
}
