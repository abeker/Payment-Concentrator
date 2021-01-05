package com.payment.bankservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerAccount extends Account {

    @Column(length = 60, unique = true)        // zato sto cuvamo enkriptovano
    private String merchantId;
    @Column(length = 100)
    private String merchantPassword;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sellerAccount")
    private Set<MerchantOrder> merchantOrders;
}
