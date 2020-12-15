package com.payment.unicreditservice.controller;

import com.payment.unicreditservice.dto.request.Payment_RequestDTO;
import com.payment.unicreditservice.dto.request.CardHolderData;
import com.payment.unicreditservice.dto.response.PaymentResponse;
import com.payment.unicreditservice.dto.response.TransactionResponse;
import com.payment.unicreditservice.services.definition.IPaymentService;
import com.payment.unicreditservice.services.definition.ITransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/")
public class RaiffeisenController {

    private final IPaymentService _paymentService;
    private final ITransactionService _transactionService;

    public RaiffeisenController(IPaymentService paymentService, ITransactionService transactionService) {
        _paymentService = paymentService;
        _transactionService = transactionService;
    }

    @PutMapping()
    public ResponseEntity<?> checkPaymentRequest(@RequestBody Payment_RequestDTO paymentRequestDTO) throws IllegalAccessException {
        return new ResponseEntity<PaymentResponse>(_paymentService.checkPaymentRequest(paymentRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody CardHolderData cardHolderData) throws IllegalAccessException, NoSuchFieldException {
        return new ResponseEntity<TransactionResponse>(_transactionService.pay(cardHolderData), HttpStatus.OK);
    }

}
