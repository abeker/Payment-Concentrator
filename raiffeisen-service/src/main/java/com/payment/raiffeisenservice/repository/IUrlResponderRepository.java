package com.payment.raiffeisenservice.repository;

import com.payment.raiffeisenservice.entity.UrlResponder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IUrlResponderRepository extends JpaRepository<UrlResponder, UUID> {
}
