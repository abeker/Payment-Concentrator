package com.payment.raiffeisenservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest extends SequenceEntity {

    @Column(precision = 10, scale = 2)
    @Type(type = "double")
    private double amount;
    private boolean deleted = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="order_id", referencedColumnName="id"),
            @JoinColumn(name="order_timestamp", referencedColumnName="currentDateTime")
    })
    private OrderCounter orderCounter;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MerchantOrder merchantOrder;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customerAccount")
    private Set<Transaction> customerAccounts;
}
