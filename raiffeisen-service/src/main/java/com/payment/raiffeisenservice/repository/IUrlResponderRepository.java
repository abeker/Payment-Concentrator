package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.UrlResponder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUrlResponderRepository extends JpaRepository<UrlResponder, UUID> {
}
