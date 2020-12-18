package com.payment.paypalservice.service;

import com.payment.paypalservice.repository.PaymentRepository;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public com.payment.paypalservice.model.Payment savePayment(Payment payment){
        com.payment.paypalservice.model.Payment payment1 = new com.payment.paypalservice.model.Payment(
                payment.getId(),
                payment.getState(),
                payment.getPayer().getPaymentMethod(),
                calculateAmount(payment.getTransactions()),
                payment.getTransactions().get(0).getAmount().getCurrency(),
                payment.getCreateTime(),
                payment.getState()
        );
       return paymentRepository.save(payment1);
    }

    public double calculateAmount(List<Transaction> transactionList){
        double sum = 0;
        for(Transaction transaction: transactionList){
            sum = sum + Double.parseDouble(transaction.getAmount().getTotal());
        }
        return sum;
    }


}
