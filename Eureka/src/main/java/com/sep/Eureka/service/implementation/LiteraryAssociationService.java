package com.sep.Eureka.service.implementation;

import com.sep.Eureka.dto.request.PaymentTypes;
import com.sep.Eureka.entity.LiteraryAssociation;
import com.sep.Eureka.entity.PaymentType;
import com.sep.Eureka.repository.ILiteraryAssociationRepository;
import com.sep.Eureka.repository.IPaymentTypeRepository;
import com.sep.Eureka.service.definition.ILiteraryAssociationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LiteraryAssociationService implements ILiteraryAssociationService {

    private final ILiteraryAssociationRepository _literaryAssociationRepository;
    private final IPaymentTypeRepository _paymentTypeRepository;

    public LiteraryAssociationService(ILiteraryAssociationRepository mapperRepository, IPaymentTypeRepository paymentTypeRepository, IPaymentTypeRepository paymentTypeRepository1) {
        _literaryAssociationRepository = mapperRepository;
        _paymentTypeRepository = paymentTypeRepository1;
    }


    @Override
    public boolean hasPaymentType(String literaryAssociationId, String paymentType) {
        LiteraryAssociation literaryAssociation = _literaryAssociationRepository.findByLuId(literaryAssociationId);
        if(literaryAssociation != null) {
            return literaryAssociation.getPaymentType().contains(_paymentTypeRepository.findByPaymentType(paymentType));
        }
        return false;
    }

    @Override
    public void addLiteraryAssociation(String luId, PaymentTypes paymentTypes) {
        LiteraryAssociation literaryAssociation = _literaryAssociationRepository.findByLuId(luId);
        if(literaryAssociation == null) {
            saveNewLiteraryAssociation(luId, paymentTypes);
            return;
        }
        updateExistingPaymentTypesOfLiteraryAssociation(literaryAssociation, paymentTypes);
    }

    private void updateExistingPaymentTypesOfLiteraryAssociation(LiteraryAssociation literaryAssociation, PaymentTypes paymentTypes) {
        Set<PaymentType> paymentTypesFromParameter = getSetOfPaymentTypes(paymentTypes);
        Set<PaymentType> oldPaymentTypes = literaryAssociation.getPaymentType();

        Set<PaymentType> filteredPaymentTypes = new HashSet<>();
        for (PaymentType paymentType : paymentTypesFromParameter) {
            if(!oldPaymentTypes.contains(paymentType)) {
                filteredPaymentTypes.add(paymentType);
            }
        }
        oldPaymentTypes.addAll(filteredPaymentTypes);
        literaryAssociation.setPaymentType(oldPaymentTypes);
        _literaryAssociationRepository.save(literaryAssociation);
    }

    private void saveNewLiteraryAssociation(String luId, PaymentTypes paymentTypes) {
        LiteraryAssociation newLiteraryAssociation = new LiteraryAssociation();
        Set<PaymentType> paymentTypesOfLiteraryAssociation = getSetOfPaymentTypes(paymentTypes);
        newLiteraryAssociation.setLuId(luId);
        newLiteraryAssociation.setPaymentType(paymentTypesOfLiteraryAssociation);
        _literaryAssociationRepository.save(newLiteraryAssociation);
    }

    private Set<PaymentType> getSetOfPaymentTypes(PaymentTypes paymentTypes) {
        Set<PaymentType> paymentTypesOfLiteraryAssociation = new HashSet<>();
        for (String paymentTypeName : paymentTypes.getPaymentTypeNames()) {
            PaymentType paymentType = _paymentTypeRepository.findByPaymentType(paymentTypeName);
            if(paymentType != null) {
                paymentTypesOfLiteraryAssociation.add(paymentType);
            }
        }
        return paymentTypesOfLiteraryAssociation;
    }
}
