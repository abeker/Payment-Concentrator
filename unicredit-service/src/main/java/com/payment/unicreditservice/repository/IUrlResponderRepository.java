package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.UrlResponder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IUrlResponderRepository extends JpaRepository<UrlResponder, UUID> {
}
