package com.payment.bitcoinservice.model;

import javax.persistence.*;

@Entity
public class BitcoinOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id")
    private String orderId;
    @Column(name="price_currency")
    private String priceCurrency;
    @Column(name="receive_currency")
    private String receiveCurrency;
    private Double priceAmount;
    private String title;
    private OrderType status;

    public BitcoinOrder() {
    }

    public BitcoinOrder(String orderId, String priceCurrency, String receiveCurrency, String title, OrderType status) {
        this.orderId = orderId;
        this.priceCurrency = priceCurrency;
        this.receiveCurrency = receiveCurrency;
        this.title = title;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public String getReceiveCurrency() {
        return receiveCurrency;
    }

    public void setReceiveCurrency(String receiveCurrency) {
        this.receiveCurrency = receiveCurrency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OrderType getStatus() {
        return status;
    }

    public void setStatus(OrderType status) {
        this.status = status;
    }

    public Double getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(Double priceAmount) {
        this.priceAmount = priceAmount;
    }
}
