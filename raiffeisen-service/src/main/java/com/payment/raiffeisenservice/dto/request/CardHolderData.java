package com.payment.raiffeisenservice.dto.request;

import lombok.Data;

@Data
public class CardHolderData {

    private String cardHolderName;
    private String accountNumber;
    private String securityCode;
    private String validThru;
    private String paymentId;

}
