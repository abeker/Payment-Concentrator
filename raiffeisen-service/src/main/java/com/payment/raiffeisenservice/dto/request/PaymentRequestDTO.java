package com.payment.raiffeisenservice.dto.request;

import lombok.Data;

@Data
public class PaymentRequestDTO {

    private String merchantId;
    private String merchantPassword;
    private double amount;

}
