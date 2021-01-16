package com.payment.raiffeisenservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Account extends BaseEntity {

    private String name;
    private double currentAmount = 0;
    @Column(unique = true, nullable = false)
    private String accountNumber;
    private String bankCode;
    private LocalDate dateOpened;
    private LocalDate dateClosed;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;
}
