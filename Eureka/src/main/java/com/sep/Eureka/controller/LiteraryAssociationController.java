package com.sep.Eureka.controller;

import com.sep.Eureka.dto.request.PaymentTypes;
import com.sep.Eureka.service.definition.ILiteraryAssociationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/literary-association")
public class LiteraryAssociationController {

    private final ILiteraryAssociationService _literaryAssociationService;

    public LiteraryAssociationController(ILiteraryAssociationService literaryAssociationService) {
        _literaryAssociationService = literaryAssociationService;
    }

    @PostMapping("/{lu_id}")
    public void register(@PathVariable("lu_id") String luId, @RequestBody PaymentTypes paymentTypes) {
        _literaryAssociationService.addLiteraryAssociation(luId, paymentTypes);
    }

    @GetMapping("/{lu_id}/{payment_type}")
    public boolean hasPaymentType(@PathVariable("lu_id") String luId, @PathVariable("payment_type") String payment_type) {
        return _literaryAssociationService.hasPaymentType(luId, payment_type);
    }

}