package com.payment.unicreditservice.dto.request;

import lombok.Data;

@Data
public class Payment_RequestDTO {

    private String merchantId;
    private String merchantPassword;
    private double amount;

}
