package com.sep.Eureka.service.implementation;

import com.sep.Eureka.dto.request.PaymentTypes;
import com.sep.Eureka.dto.response.LiteraryAssociationResponse;
import com.sep.Eureka.entity.LiteraryAssociation;
import com.sep.Eureka.entity.PaymentType;
import com.sep.Eureka.repository.ILiteraryAssociationRepository;
import com.sep.Eureka.repository.IPaymentTypeRepository;
import com.sep.Eureka.service.definition.ILiteraryAssociationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    public boolean hasPaymentType(String literaryAssociationId, String paymentTypeName) {
        LiteraryAssociation literaryAssociation = findByLuId(literaryAssociationId);
        PaymentType paymentType = findByPaymentType(paymentTypeName);
        if(literaryAssociation != null && paymentType != null) {
            return literaryAssociation.getPaymentType().contains(paymentType);
        }
        return false;
    }

    @Override
    public void add(String luId, PaymentTypes paymentTypes) {
        LiteraryAssociation literaryAssociation = findByLuId(luId);
        if(literaryAssociation == null) {
            saveNewLiteraryAssociation(luId, paymentTypes);
            return;
        }
        updateExistingPaymentTypesOfLiteraryAssociation(literaryAssociation, paymentTypes);
    }

    @Override
    public void delete(String literaryAssociationId) {
        LiteraryAssociation literaryAssociation = findByLuId(literaryAssociationId);
        if(literaryAssociation != null) {
            literaryAssociation.setDeleted(true);
            _literaryAssociationRepository.save(literaryAssociation);
        }
    }

    @Override
    public void deleteMapping(String literaryAssociationId, String paymentTypeName) {
        LiteraryAssociation literaryAssociation = findByLuId(literaryAssociationId);
        PaymentType paymentType = findByPaymentType(paymentTypeName);
        if(literaryAssociation != null && paymentType != null) {
            literaryAssociation.getPaymentType().remove(paymentType);
        }
    }

    @Override
    public List<String> getPaymentTypes(String luId) {
        LiteraryAssociation literaryAssociation = findByLuId(luId);
        List<String> retPaymentTypeNames = new ArrayList<>();
        if(literaryAssociation != null) {
            for (PaymentType paymentType : literaryAssociation.getPaymentType()) {
                retPaymentTypeNames.add(paymentType.getPaymentType());
            }
        }
        return retPaymentTypeNames;
    }

    private void updateExistingPaymentTypesOfLiteraryAssociation(LiteraryAssociation literaryAssociation, PaymentTypes paymentTypes) {
        Set<PaymentType> paymentTypesFromParameter = getSetOfPaymentTypes(paymentTypes);
        Set<PaymentType> oldPaymentTypes = literaryAssociation.getPaymentType();

        Set<PaymentType> filteredPaymentTypes = new HashSet<>();
        for (PaymentType paymentType : paymentTypesFromParameter) {
            if(!oldPaymentTypes.contains(paymentType) && !paymentType.isDeleted()) {
                filteredPaymentTypes.add(paymentType);
            }
        }
        oldPaymentTypes.addAll(filteredPaymentTypes);
        literaryAssociation.setPaymentType(oldPaymentTypes);
        _literaryAssociationRepository.save(literaryAssociation);
    }

    private LiteraryAssociation saveNewLiteraryAssociation(String luId, PaymentTypes paymentTypes) {
        LiteraryAssociation newLiteraryAssociation = new LiteraryAssociation();
        Set<PaymentType> paymentTypesOfLiteraryAssociation = getSetOfPaymentTypes(paymentTypes);
        newLiteraryAssociation.setLuId(luId);
        newLiteraryAssociation.setPaymentType(paymentTypesOfLiteraryAssociation);
        return _literaryAssociationRepository.save(newLiteraryAssociation);
    }

    private Set<PaymentType> getSetOfPaymentTypes(PaymentTypes paymentTypes) {
        Set<PaymentType> paymentTypesOfLiteraryAssociation = new HashSet<>();
        for (String paymentTypeName : paymentTypes.getPaymentTypeNames()) {
            PaymentType paymentType = findByPaymentType(paymentTypeName);
            if(paymentType != null) {
                paymentTypesOfLiteraryAssociation.add(paymentType);
            }
        }
        return paymentTypesOfLiteraryAssociation;
    }

    private PaymentType findByPaymentType(String paymentTypeName) {
        PaymentType paymentType = _paymentTypeRepository.findByPaymentType(paymentTypeName);
        if(paymentType != null) {
            if(!paymentType.isDeleted()) {
                return paymentType;
            }
        }
        return null;
    }

    private LiteraryAssociation findByLuId(String luId) {
        LiteraryAssociation literaryAssociation = _literaryAssociationRepository.findByLuId(luId);
        if(literaryAssociation != null) {
            if(!literaryAssociation.isDeleted()) {
                return literaryAssociation;
            }
        }
        return null;
    }
}
