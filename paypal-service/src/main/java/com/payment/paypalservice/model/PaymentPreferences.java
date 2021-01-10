package com.payment.paypalservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPreferences {
    private Boolean auto_bill_outstanding;
    private SetupFee setup_fee;
    private String setup_fee_failure_action;
    private  Integer payment_failure_threshold;
}
