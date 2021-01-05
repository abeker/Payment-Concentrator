package com.payment.bankservice.repository;

import com.payment.bankservice.entity.MerchantOrder;
import com.payment.bankservice.entity.SellerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IMerchantOrderRepository extends JpaRepository<MerchantOrder, Integer> {

    Set<MerchantOrder> findAllBySellerAccount(SellerAccount sellerAccount);

}
