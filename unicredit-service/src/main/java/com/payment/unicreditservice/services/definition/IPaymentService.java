package com.payment.unicreditservice.services.definition;

import com.payment.unicreditservice.dto.request.PaymentRequestDTO;
import com.payment.unicreditservice.dto.response.PaymentRequestStatus;
import com.payment.unicreditservice.dto.response.PaymentResponse;

public interface IPaymentService {

    PaymentResponse checkPaymentRequest(PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException;

    void cancelRequest(String paymentId);

    PaymentRequestStatus checkRequestStatus(String paymentId);

}
