package com.payment.paypalservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    private String PaymentId;
    private String paymentState;
    private String paymentMethod;
    private Double amount;
    private String currency;
    private String paymentTime;
    private String state;

    public Payment() {
    }

    public Payment(String paymentId, String paymentState, String paymentMethod, Double amount, String currency, String paymentTime, String state) {
        PaymentId = paymentId;
        this.paymentState = paymentState;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.currency = currency;
        this.paymentTime = paymentTime;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(String paymentId) {
        PaymentId = paymentId;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
