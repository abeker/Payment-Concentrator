package com.payment.unicreditservice.controller;

import com.payment.unicreditservice.dto.request.PaymentRequestDTO;
import com.payment.unicreditservice.dto.request.CardHolderData;
import com.payment.unicreditservice.dto.request.RequestPcc;
import com.payment.unicreditservice.dto.response.PaymentResponse;
import com.payment.unicreditservice.dto.response.RegisterBank;
import com.payment.unicreditservice.dto.response.ResponsePcc;
import com.payment.unicreditservice.dto.response.TransactionResponse;
import com.payment.unicreditservice.feign.EurekaClient;
import com.payment.unicreditservice.services.definition.IBankService;
import com.payment.unicreditservice.services.definition.IPaymentService;
import com.payment.unicreditservice.services.definition.ITransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/")
public class UnicreditController {

    private final IPaymentService _paymentService;
    private final ITransactionService _transactionService;
    private final IBankService _bankService;
    private final EurekaClient _eurekaClient;

    public UnicreditController(IPaymentService paymentService, ITransactionService transactionService, IBankService bankService, EurekaClient eurekaClient) {
        _paymentService = paymentService;
        _transactionService = transactionService;
        _bankService = bankService;
        _eurekaClient = eurekaClient;
    }

    @PutMapping()
    public ResponseEntity<?> checkPaymentRequest(@RequestBody PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException {
        List<RegisterBank> registeredBanks = _eurekaClient.getBanks();
        _bankService.registerBanks(registeredBanks);
        return new ResponseEntity<PaymentResponse>(_paymentService.checkPaymentRequest(paymentRequestDTO), HttpStatus.OK);
    }

    /**
     * @param bankName - u kojoj banci je napravljen request (banka prodavca)
     * */
    @PostMapping("/pay/{bankName}")
    public ResponseEntity<?> pay(@RequestBody CardHolderData cardHolderData, @PathVariable("bankName") String bankName) throws IllegalAccessException, NoSuchFieldException {
        return new ResponseEntity<TransactionResponse>(_transactionService.pay(cardHolderData, bankName), HttpStatus.OK);
    }

    @PostMapping("/pay/pcc")
    public ResponseEntity<?> payPcc(@RequestBody RequestPcc requestPcc) throws IllegalAccessException {
        return new ResponseEntity<ResponsePcc>(_transactionService.payPcc(requestPcc), HttpStatus.OK);
    }

}
