package com.payment.bankservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bank extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String bankCode;
    private int orderCounterAcquirer = 0;
    private int orderCounterIssuer = 0;
    private int paymentCounter = 0;

}
