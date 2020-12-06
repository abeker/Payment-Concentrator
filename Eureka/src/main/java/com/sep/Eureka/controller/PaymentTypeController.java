package com.sep.Eureka.controller;

import com.sep.Eureka.dto.request.PaymentTypes;
import com.sep.Eureka.service.definition.IPaymentTypeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-type")
public class PaymentTypeController {

    private final IPaymentTypeService _paymentTypeService;

    public PaymentTypeController(IPaymentTypeService paymentTypeService) {
        _paymentTypeService = paymentTypeService;
    }

    @GetMapping("")
    public PaymentTypes getAll(){ return _paymentTypeService.getAll(); }

}
