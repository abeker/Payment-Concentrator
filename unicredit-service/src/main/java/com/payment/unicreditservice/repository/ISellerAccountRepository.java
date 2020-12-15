package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.SellerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ISellerAccountRepository extends JpaRepository<SellerAccount, UUID> {

    Optional<SellerAccount> findByMerchantId(String merchantId);

}
