package com.payment.unicreditservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponder extends BaseEntity {

    private String paymentUrl;
    private LocalDate dateOpened;
    private LocalDate dateClosed;
}
