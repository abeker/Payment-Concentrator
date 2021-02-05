package com.payment.unicreditservice.controller;

import com.payment.unicreditservice.dto.request.PaymentRequestDTO;
import com.payment.unicreditservice.dto.request.CardHolderData;
import com.payment.unicreditservice.dto.request.RequestPcc;
import com.payment.unicreditservice.dto.response.PaymentRequestStatus;
import com.payment.unicreditservice.dto.response.PaymentResponse;
import com.payment.unicreditservice.dto.response.ResponsePcc;
import com.payment.unicreditservice.dto.response.TransactionResponse;
import com.payment.unicreditservice.services.definition.IPaymentService;
import com.payment.unicreditservice.services.definition.ITransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/")
public class UnicreditController {

    private final IPaymentService _paymentService;
    private final ITransactionService _transactionService;

    public UnicreditController(IPaymentService paymentService, ITransactionService transactionService) {
        _paymentService = paymentService;
        _transactionService = transactionService;
    }

    @PutMapping()
    public ResponseEntity<?> checkPaymentRequest(@RequestBody PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException {
        return new ResponseEntity<PaymentResponse>(_paymentService.checkPaymentRequest(paymentRequestDTO), HttpStatus.OK);
    }

    @PutMapping("/{paymentId}/cancel")
    public void cancelRequest(@PathVariable("paymentId") String paymentId) {
        _paymentService.cancelRequest(paymentId);
    }

    @GetMapping("/{paymentId}/status")
    public ResponseEntity<PaymentRequestStatus> checkPaymentRequestStatus(@PathVariable("paymentId") String paymentId) {
        return new ResponseEntity<PaymentRequestStatus>(_paymentService.checkRequestStatus(paymentId), HttpStatus.OK);
    }

    @PostMapping("/pay")
    public ResponseEntity<TransactionResponse> pay(@RequestBody CardHolderData cardHolderData) throws IllegalAccessException, NoSuchFieldException {
        return new ResponseEntity<TransactionResponse>(_transactionService.pay(cardHolderData), HttpStatus.OK);
    }

    @PostMapping("/pay/pcc")
    public ResponseEntity<?> payPcc(@RequestBody RequestPcc requestPcc) throws IllegalAccessException {
        return new ResponseEntity<ResponsePcc>(_transactionService.payPcc(requestPcc), HttpStatus.OK);
    }

}
