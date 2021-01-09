package com.payment.bankservice.controller;

import com.payment.bankservice.dto.request.AccountRequest;
import com.payment.bankservice.services.definition.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/account")
public class AccountController {

    private final IAccountService _accountService;

    public AccountController(IAccountService accountService) {
        _accountService = accountService;
    }

    @PostMapping()
    public ResponseEntity<?> createSellerAccount(@RequestBody AccountRequest accountRequest) {
        return new ResponseEntity<>(_accountService.createSellerAccount(accountRequest), HttpStatus.OK);
    }

}
