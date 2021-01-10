package com.payment.paypalservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Frequency {
    private String interval_unit; //MONTH
    private Integer interval_count;
}
