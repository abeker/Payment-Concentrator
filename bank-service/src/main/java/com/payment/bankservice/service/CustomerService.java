package com.payment.bankservice.service;

import com.payment.bankservice.model.Customer;
import com.payment.bankservice.repository.CustomerRepository;
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
