package com.sep.Eureka.service.definition;

import com.sep.Eureka.dto.request.PaymentTypes;

public interface ILiteraryAssociationService {

//    LiteraryAssociation addLiteraryAssociation(String literaryAssociationId, String paymentType);
    boolean hasPaymentType(String literaryAssociationId, String paymentType);

    void addLiteraryAssociation(String luId, PaymentTypes paymentTypes);
}
