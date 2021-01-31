package com.sep.Eureka.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiteraryAssociation extends BaseEntity {

    private String luId;        // literaryAssociation id

    private boolean deleted = false;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "literary_association_payment_type",
            joinColumns = @JoinColumn(name = "payment_type_id"),
            inverseJoinColumns = @JoinColumn(name = "literary_association_id")
    )
    private Set<PaymentType> paymentType;

}

