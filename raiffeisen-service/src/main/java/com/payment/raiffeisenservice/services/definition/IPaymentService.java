package com.payment.raiffeisenservice.services.definition;


import com.payment.raiffeisenservice.dto.request.PaymentRequestDTO;
import com.payment.raiffeisenservice.dto.response.PaymentRequestStatus;
import com.payment.raiffeisenservice.dto.response.PaymentResponse;

public interface IPaymentService {

    PaymentResponse checkPaymentRequest(PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException;

    void cancelRequest(String paymentId);

    PaymentRequestStatus checkRequestStatus(String paymentId);
}
