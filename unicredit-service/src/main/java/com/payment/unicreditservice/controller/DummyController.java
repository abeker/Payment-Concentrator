package com.payment.unicreditservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/visa")
public class DummyController {

    @GetMapping()
    public ResponseEntity<?> hello(){
        return new ResponseEntity<String>("Hello from Visa", HttpStatus.OK);
    }

}
