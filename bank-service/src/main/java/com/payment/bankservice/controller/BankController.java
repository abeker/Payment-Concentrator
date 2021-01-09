package com.payment.bankservice.controller;

import com.payment.bankservice.dto.request.CardHolderData;
import com.payment.bankservice.dto.request.PaymentRequestDTO;
import com.payment.bankservice.dto.request.RequestPcc;
import com.payment.bankservice.dto.response.*;
import com.payment.bankservice.feign.EurekaClient;
import com.payment.bankservice.services.definition.IBankService;
import com.payment.bankservice.services.definition.IPaymentService;
import com.payment.bankservice.services.definition.ITransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@RestController
@RequestMapping(value="/")
public class BankController {

    private final IPaymentService _paymentService;
    private final ITransactionService _transactionService;
    private final IBankService _bankService;
    private final EurekaClient _eurekaClient;

    public BankController(IPaymentService paymentService, ITransactionService transactionService, IBankService bankService, EurekaClient eurekaClient) {
        _paymentService = paymentService;
        _transactionService = transactionService;
        _bankService = bankService;
        _eurekaClient = eurekaClient;
    }

    @GetMapping()
    public ResponseEntity<?> getBanks() throws IllegalAccessException {
        return new ResponseEntity<>(_bankService.getBanks(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> checkPaymentRequest(@RequestBody PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException {
        List<RegisterBank> registeredBanks = _eurekaClient.getBanks();
        _bankService.registerBanks(registeredBanks);
        return new ResponseEntity<>(_paymentService.checkPaymentRequest(paymentRequestDTO), HttpStatus.OK);
    }

    /**
     * @param bankName - u kojoj banci je napravljen request (banka prodavca)
     * */
    @PostMapping("/pay/{bankName}")
    public ResponseEntity<?> pay(@RequestBody CardHolderData cardHolderData, @PathVariable("bankName") String bankName) throws IllegalAccessException, NoSuchFieldException {
        return new ResponseEntity<>(_transactionService.pay(cardHolderData, bankName), HttpStatus.OK);
    }

    @PostMapping("/pay/pcc")
    public ResponseEntity<?> payPcc(@RequestBody RequestPcc requestPcc) throws IllegalAccessException {
        return new ResponseEntity<>(_transactionService.payPcc(requestPcc), HttpStatus.OK);
    }

}
