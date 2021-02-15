package com.sep.Eureka.service.definition;

import com.sep.Eureka.dto.request.PaymentTypes;

import java.util.List;

public interface ILiteraryAssociationService {

    void add(String luId, PaymentTypes paymentTypes);
    void delete(String literaryAssociationId);
    boolean hasPaymentType(String literaryAssociationId, String paymentType);
    void deleteMapping(String literaryAssociationId, String paymentTypeName);
    List<String> getPaymentTypes(String luId);
}
