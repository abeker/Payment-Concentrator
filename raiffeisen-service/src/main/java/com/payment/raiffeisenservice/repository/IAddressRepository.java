package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAddressRepository extends JpaRepository<Address, UUID> {
}
