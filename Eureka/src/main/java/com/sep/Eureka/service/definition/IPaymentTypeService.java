package com.sep.Eureka.service.definition;

import com.sep.Eureka.dto.request.PaymentTypes;
import com.sep.Eureka.entity.PaymentType;

public interface IPaymentTypeService {

    PaymentType add(String paymentType);
    PaymentTypes getAll();
    void delete(String paymentTypeName);
}
