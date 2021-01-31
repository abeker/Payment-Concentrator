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

    @Column(nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private String port;

    @Column(nullable = false)
    private String secureVipAddress;

    private String bankCode;

    private boolean deleted = false;

    @ManyToMany(mappedBy = "paymentType")
    private Set<LiteraryAssociation> literaryAssociation;

}
