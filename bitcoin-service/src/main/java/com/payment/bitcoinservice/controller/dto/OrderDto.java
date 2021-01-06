package com.payment.bitcoinservice.controller.dto;

public class OrderDto {
    private String order_id;
    private Double price_amount;
    private String price_currency;
    private String receive_currency;
    //optional
    private String title;
    private String callback_url;
    private String cancel_url;
    private String success_url;
    private String callback_token;

    public OrderDto() {
    }

    public OrderDto(String order_id, Double price_amount, String price_currency, String receive_currency, String title, String callback_url, String cancel_url, String success_url, String callback_token) {
        this.order_id = order_id;
        this.price_amount = price_amount;
        this.price_currency = price_currency;
        this.receive_currency = receive_currency;
        this.title = title;
        this.callback_url = callback_url;
        this.cancel_url = cancel_url;
        this.success_url = success_url;
        this.callback_token = callback_token;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Double getPrice_amount() {
        return price_amount;
    }

    public void setPrice_amount(Double price_amount) {
        this.price_amount = price_amount;
    }

    public String getPrice_currency() {
        return price_currency;
    }

    public void setPrice_currency(String price_currency) {
        this.price_currency = price_currency;
    }

    public String getReceive_currency() {
        return receive_currency;
    }

    public void setReceive_currency(String receive_currency) {
        this.receive_currency = receive_currency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public String getCancel_url() {
        return cancel_url;
    }

    public void setCancel_url(String cancel_url) {
        this.cancel_url = cancel_url;
    }

    public String getSuccess_url() {
        return success_url;
    }

    public void setSuccess_url(String success_url) {
        this.success_url = success_url;
    }

    public String getCallback_token() {
        return callback_token;
    }

    public void setCallback_token(String callback_token) {
        this.callback_token = callback_token;
    }
}
