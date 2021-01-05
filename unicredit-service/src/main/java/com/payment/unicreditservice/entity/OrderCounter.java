package com.payment.unicreditservice.entity;

import com.payment.unicreditservice.util.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCounter extends BaseEntity {

//    // sequence counter for cross-bank transactions (different banks have different counters)
//    private int counter;

    private int counter;
    private LocalDateTime currentDateTime;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private String bankCode;

}
