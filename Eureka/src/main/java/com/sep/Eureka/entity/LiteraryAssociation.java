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
public class LiteraryAssociation extends BaseEntity {

    private String luId;         // literaryAssociation - id

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PaymentType> paymentType;

}

