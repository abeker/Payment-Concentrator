package com.payment.raiffeisenservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantOrder extends SequenceEntity {

    private LocalDateTime dateOpened;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private SellerAccount sellerAccount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "merchantOrder")
    private PaymentRequest paymentRequest;
}
