package com.payment.bankservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private String paymentUrl;
    private String paymentId;
}
