package com.payment.raiffeisenservice.entity;

import com.payment.raiffeisenservice.util.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CustomerAccount customerAccount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PaymentRequest paymentRequest;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
