package com.payment.bankservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class BankResponse {

    private String id;
    private String name;
    private String bankCode;

}
