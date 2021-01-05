package com.sep.Eureka.service.definition;

import com.sep.Eureka.dto.request.PaymentTypes;
import com.sep.Eureka.dto.request.RegisterBank;
import com.sep.Eureka.entity.PaymentType;

import java.util.List;

public interface IPaymentTypeService {

    PaymentType add(String paymentType, String port, String secureVipAddress);
    PaymentTypes getAll();
    void delete(String paymentTypeName);
    List<RegisterBank> getBanks();
}
