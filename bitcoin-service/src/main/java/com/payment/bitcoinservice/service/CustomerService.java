package com.payment.bitcoinservice.service;

import com.payment.bitcoinservice.model.Customer;
import com.payment.bitcoinservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer>findAllCustomers(){
        return customerRepository.findAll();
    }
}
