package com.payment.bitcoinservice.model;

import com.payment.bitcoinservice.util.enums.SubscriberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscriber {

    @Id
    private Long id;

    private String email;
    private String organisation_name;
    private String address;
    private String city;
    private String country;
    private String postal_code;
    private String subsciber_id;
    private String authkey;

    @Enumerated(EnumType.STRING)
    private SubscriberStatus subscriberStatus;

}
