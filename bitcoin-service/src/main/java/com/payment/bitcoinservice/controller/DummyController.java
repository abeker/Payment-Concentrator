package com.payment.bitcoinservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/bitcoin")
public class DummyController {

    @GetMapping()
    public ResponseEntity<?> hello(){
        return new ResponseEntity<String>(, HttpStatus.OK);
    }

}
