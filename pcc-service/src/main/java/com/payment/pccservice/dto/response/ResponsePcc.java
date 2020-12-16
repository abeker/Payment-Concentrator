package com.payment.pccservice.dto.response;

import lombok.Data;

@Data
public class ResponsePcc {

    private int acquirerOrderId;
    private String acquirerOrderTimestamp;
    private int issuerOrderId;
    private String issuerOrderTimestamp;

}
