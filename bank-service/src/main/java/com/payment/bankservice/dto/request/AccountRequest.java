package com.payment.bankservice.dto.request;

import lombok.Data;

@Data
public class AccountRequest {

    private String name;
    private String country;
    private String city;
    private String street;
    private String zipCode;
    private String bankCode;

}
