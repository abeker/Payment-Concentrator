package com.payment.bitcoinservice.model;

import com.payment.bitcoinservice.util.enums.SubscriberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscriber extends BaseEntity {

    private String email;
    private String organisationName;
    private String address;
    private String city;
    private String country;
    private String postalCode;

    @Enumerated(EnumType.STRING)
    private SubscriberStatus subscriberStatus;

}
