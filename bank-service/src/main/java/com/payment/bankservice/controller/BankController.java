package com.payment.bankservice.controller;

import com.payment.bankservice.model.Customer;
import com.payment.bankservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankController {
    @Autowired
    private CustomerService customerService;

    @CrossOrigin(origins = "*")
    @GetMapping("/pay")
    public String pay(){
        return "Successfully paid using bank";
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>>getAllCustomers(){
        List<Customer> customers =  customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }
}
