package com.sep.Eureka.service.implementation;

import com.sep.Eureka.entity.PaymentType;
import com.sep.Eureka.repository.IPaymentTypeRepository;
import com.sep.Eureka.service.definition.IPaymentTypeService;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class PaymentTypeService implements IPaymentTypeService {

    private final IPaymentTypeRepository _paymentTypeRepository;

    public PaymentTypeService(IPaymentTypeRepository paymentTypeRepository) {
        _paymentTypeRepository = paymentTypeRepository;
    }

    @Override
    public PaymentType addPaymentType(String paymentTypeName) {
        PaymentType storedPaymentType = _paymentTypeRepository.findByPaymentType(paymentTypeName);
        if(storedPaymentType != null) {
            return storedPaymentType;
        }
        PaymentType paymentType = new PaymentType();
        paymentType.setPaymentType(paymentTypeName);
        paymentType.setLiteraryAssociation(new HashSet<>());
        return _paymentTypeRepository.save(paymentType);
    }
}
