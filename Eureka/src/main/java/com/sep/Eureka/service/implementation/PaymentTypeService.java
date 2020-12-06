package com.sep.Eureka.service.implementation;

import com.sep.Eureka.dto.request.PaymentTypes;
import com.sep.Eureka.entity.PaymentType;
import com.sep.Eureka.repository.IPaymentTypeRepository;
import com.sep.Eureka.service.definition.IPaymentTypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@Service
public class PaymentTypeService implements IPaymentTypeService {

    private final IPaymentTypeRepository _paymentTypeRepository;

    public PaymentTypeService(IPaymentTypeRepository paymentTypeRepository) {
        _paymentTypeRepository = paymentTypeRepository;
    }

    @Override
    public PaymentType add(String paymentTypeName) {
        PaymentType storedPaymentType = findByPaymentTypeName(paymentTypeName);
        if(storedPaymentType != null) {
            return storedPaymentType;
        }
        PaymentType paymentType = new PaymentType();
        paymentType.setPaymentType(paymentTypeName);
        paymentType.setLiteraryAssociation(new HashSet<>());
        return _paymentTypeRepository.save(paymentType);
    }

    @Override
    public PaymentTypes getAll() {
        PaymentTypes retPaymentTypes = new PaymentTypes();
        List<String> nameOfPaymentTypes = new ArrayList<>();
        for (PaymentType paymentType : _paymentTypeRepository.findAll()) {
            if(!paymentType.isDeleted()) {
                nameOfPaymentTypes.add(paymentType.getPaymentType());
            }
        }
        return retPaymentTypes;
    }

    @Override
    public void delete(String paymentTypeName) {
        PaymentType paymentType = findByPaymentTypeName(paymentTypeName);
        if(paymentType != null) {
            paymentType.setDeleted(true);
            _paymentTypeRepository.save(paymentType);
        }
    }

    private PaymentType findByPaymentTypeName(String paymentTypeName) {
        PaymentType retPaymentType = _paymentTypeRepository.findByPaymentType(paymentTypeName);
        if(retPaymentType != null) {
            if(!retPaymentType.isDeleted()) {
                return retPaymentType;
            }
        }
        return null;
    }
}
