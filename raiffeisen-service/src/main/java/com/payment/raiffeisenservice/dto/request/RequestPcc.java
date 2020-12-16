package com.payment.raiffeisenservice.dto.request;

import lombok.Data;

@Data
public class RequestPcc {

    private String accountNumber;
    private String securityCode;
    private String validThru;
    private String name;
    private double amount;
    private int acquirerOrderId;
    private String acquirerOrderTimestamp;

}
