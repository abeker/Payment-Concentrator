package com.payment.unicreditservice.services.definition;

import com.payment.unicreditservice.dto.request.PaymentRequestDTO;
import com.payment.unicreditservice.dto.response.PaymentResponse;

public interface IPaymentService {

    PaymentResponse checkPaymentRequest(PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException;

}
