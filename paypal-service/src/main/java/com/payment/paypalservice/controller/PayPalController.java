package com.payment.paypalservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayPalController {

    @CrossOrigin(value = "*")
    @GetMapping("/pay")
    public String payWithPayPal(){
        return "Successfully paid with PayPal!";
    }
}
