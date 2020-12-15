package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.UrlResponder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUrlResponderRepository extends JpaRepository<UrlResponder, UUID> {
}
