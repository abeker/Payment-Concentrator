package com.payment.unicreditservice.services.definition;

import com.payment.unicreditservice.dto.request.Payment_RequestDTO;
import com.payment.unicreditservice.dto.response.PaymentResponse;

public interface IPaymentService {

    PaymentResponse checkPaymentRequest(Payment_RequestDTO paymentRequestDTO) throws IllegalAccessException;

}
