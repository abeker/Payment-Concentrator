package com.payment.bankservice.repository;

import com.payment.bankservice.entity.SellerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ISellerAccountRepository extends JpaRepository<SellerAccount, UUID> {

    Optional<SellerAccount> findByMerchantId(String merchantId);

}
