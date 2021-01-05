package com.payment.unicreditservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantOrder extends BaseEntity {

    // sequence counter for merchant transaction
    private int counter;

    private LocalDateTime dateOpened;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private SellerAccount sellerAccount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "merchantOrder")
    private PaymentRequest paymentRequest;

}
