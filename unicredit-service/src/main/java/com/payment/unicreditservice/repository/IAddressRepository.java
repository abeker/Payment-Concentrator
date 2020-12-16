package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IAddressRepository extends JpaRepository<Address, UUID> {
}
