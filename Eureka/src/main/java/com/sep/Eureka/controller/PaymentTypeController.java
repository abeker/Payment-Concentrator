package com.sep.Eureka.controller;

import com.sep.Eureka.service.definition.IPaymentTypeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-type")
public class PaymentTypeController {

    private final IPaymentTypeService _paymentTypeService;

    public PaymentTypeController(IPaymentTypeService paymentTypeService) {
        _paymentTypeService = paymentTypeService;
    }

    @PostMapping("/{name}")
    public void register(@PathVariable("name") String paymentType) {
        _paymentTypeService.addPaymentType(paymentType);
    }

}
