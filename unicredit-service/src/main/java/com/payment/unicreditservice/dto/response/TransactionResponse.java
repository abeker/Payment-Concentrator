package com.payment.unicreditservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionResponse {

    private String merchantOrderId;
    private String acquirerOrderId;
    private String acquirerTimestamp;
    private String paymentId;

}
