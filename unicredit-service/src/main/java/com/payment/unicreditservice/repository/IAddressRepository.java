package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAddressRepository extends JpaRepository<Address, UUID> {
}
