package com.payment.unicreditservice.repository;

import com.payment.unicreditservice.entity.MerchantOrder;
import com.payment.unicreditservice.entity.SellerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IMerchantOrderRepository extends JpaRepository<MerchantOrder, Integer> {

    Set<MerchantOrder> findAllBySellerAccount(SellerAccount sellerAccount);

}
