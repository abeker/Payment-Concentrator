package com.payment.bitcoinservice.dto.request;

import lombok.Data;

@Data
public class SubscriberRequest {

    private String email;
    private String organisationName;
    private String address;
    private String city;
    private String country;
    private String postalCode;

}
