package com.sep.Eureka.entity;

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
public class PaymentType extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String paymentType;

    private boolean deleted = false;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LiteraryAssociation> literaryAssociation;

}
