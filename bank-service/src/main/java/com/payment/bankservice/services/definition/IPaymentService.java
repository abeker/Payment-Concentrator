package com.payment.bankservice.services.definition;


import com.payment.bankservice.dto.request.PaymentRequestDTO;
import com.payment.bankservice.dto.response.PaymentResponse;

public interface IPaymentService {

    PaymentResponse checkPaymentRequest(PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException;

}
