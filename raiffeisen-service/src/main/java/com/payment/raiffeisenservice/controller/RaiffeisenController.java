package com.payment.raiffeisenservice.controller;

import com.payment.raiffeisenservice.dto.request.CardHolderData;
import com.payment.raiffeisenservice.dto.request.PaymentRequestDTO;
import com.payment.raiffeisenservice.dto.request.RequestPcc;
import com.payment.raiffeisenservice.dto.response.PaymentRequestStatus;
import com.payment.raiffeisenservice.dto.response.PaymentResponse;
import com.payment.raiffeisenservice.dto.response.ResponsePcc;
import com.payment.raiffeisenservice.dto.response.TransactionResponse;
import com.payment.raiffeisenservice.services.definition.IPaymentService;
import com.payment.raiffeisenservice.services.definition.ITransactionService;
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

    @PutMapping("/{paymentId}/cancel")
    public void cancelRequest(@PathVariable("paymentId") String paymentId) {
        _paymentService.cancelRequest(paymentId);
    }

    @GetMapping("/{paymentId}/status")
    public ResponseEntity<PaymentRequestStatus> checkPaymentRequestStatus(@PathVariable("paymentId") String paymentId) {
        return new ResponseEntity<PaymentRequestStatus>(_paymentService.checkRequestStatus(paymentId), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> checkPaymentRequest(@RequestBody PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException {
        return new ResponseEntity<PaymentResponse>(_paymentService.checkPaymentRequest(paymentRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody CardHolderData cardHolderData) throws IllegalAccessException, NoSuchFieldException {
        return new ResponseEntity<TransactionResponse>(_transactionService.pay(cardHolderData), HttpStatus.OK);
    }

    @PostMapping("/pay/pcc")
    public ResponseEntity<?> payPcc(@RequestBody RequestPcc requestPcc) throws IllegalAccessException {
        return new ResponseEntity<ResponsePcc>(_transactionService.payPcc(requestPcc), HttpStatus.OK);
    }

}