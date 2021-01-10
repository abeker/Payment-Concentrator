package com.payment.paypalservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillingCycle {
    private Frequency frequency;
    private String tenure_type; //TRIAL,
    private Integer sequence;
    private Integer total_cycles;
    private PricingScheme pricing_scheme;
}
