package com.sep.Eureka.service.implementation;

import com.sep.Eureka.dto.request.PaymentTypes;
import com.sep.Eureka.dto.request.RegisterBank;
import com.sep.Eureka.entity.LiteraryAssociation;
import com.sep.Eureka.entity.PaymentType;
import com.sep.Eureka.repository.ILiteraryAssociationRepository;
import com.sep.Eureka.repository.IPaymentTypeRepository;
import com.sep.Eureka.service.definition.IPaymentTypeService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@Service
public class PaymentTypeService implements IPaymentTypeService {

    private final IPaymentTypeRepository _paymentTypeRepository;
    private final ILiteraryAssociationRepository _literaryAssociationRepository;

    public PaymentTypeService(IPaymentTypeRepository paymentTypeRepository, ILiteraryAssociationRepository literaryAssociationRepository) {
        _paymentTypeRepository = paymentTypeRepository;
        _literaryAssociationRepository = literaryAssociationRepository;
    }

    ReentrantLock lockBankSaving = new ReentrantLock();
    @Override
    public PaymentType add(String paymentTypeName, String port, String secureVipAddress) {
        lockBankSaving.lock();
        try {
            PaymentType storedPaymentType = findByPaymentType(paymentTypeName, secureVipAddress);
            if(storedPaymentType != null) {
                return storedPaymentType;
            }
            return savePaymentType(paymentTypeName, port, secureVipAddress);
        } finally {
            lockBankSaving.unlock();
        }
    }

    private PaymentType savePaymentType(String paymentTypeName, String port, String secureVipAddress) {
        PaymentType paymentType = new PaymentType();
        paymentType.setPort(port);
        paymentType.setLiteraryAssociation(new HashSet<>());
        paymentType.setSecureVipAddress(secureVipAddress);
        int numberOfBanks = getNumberOfBanks(paymentTypeName);
        if (numberOfBanks > 0) {
            paymentType.setPaymentType(paymentTypeName + numberOfBanks);
        } else {
            paymentType.setPaymentType(paymentTypeName);
        }
        setBankCode(paymentType, numberOfBanks);
        PaymentType savedPaymentType = _paymentTypeRepository.save(paymentType);
        addPaymentsToPredefinedLiteraryAssociation(savedPaymentType);

        return savedPaymentType;
    }

    private void addPaymentsToPredefinedLiteraryAssociation(PaymentType paymentType) {
        LiteraryAssociation literaryAssociation = _literaryAssociationRepository.findByLuId("d0c213f7-1e95-4ce4-8a65-334071e31ce8");
        if(literaryAssociation != null) {
            literaryAssociation.getPaymentType().add(paymentType);
            _literaryAssociationRepository.save(literaryAssociation);
        }
    }

    private void setBankCode(PaymentType paymentType, int numberOfBanks) {
        if(numberOfBanks == 0) {
            paymentType.setBankCode("99273");
        } else if(numberOfBanks == 1) {
            paymentType.setBankCode("89454");
        } else if(numberOfBanks > 1) {
            paymentType.setBankCode(String.valueOf(genBankCode()));
        } else if(numberOfBanks == -1) {
            paymentType.setBankCode(null);
        }
    }

    public int genBankCode() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    private int getNumberOfBanks(String paymentTypeName) {
        int counter = 0;
        boolean isBank = isThisBank(paymentTypeName);
        for (PaymentType paymentType : _paymentTypeRepository.findAll()) {
            if(paymentType.getPaymentType().toLowerCase().startsWith(paymentTypeName.substring(0,3)) && isBank) {
                counter += 1;
            }
        }
        return !isBank ? -1 : counter;
    }

    private boolean isThisBank(String paymentTypeName) {
        return paymentTypeName.toLowerCase().contains("bank") ||
                paymentTypeName.toLowerCase().contains("unicredit") ||
                    paymentTypeName.toLowerCase().contains("raiffeisen");
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
        retPaymentTypes.setPaymentTypeNames(nameOfPaymentTypes);
        return retPaymentTypes;
    }

    @Override
    public void delete(String paymentTypeName) {
        PaymentType paymentType = _paymentTypeRepository.findByPaymentType(paymentTypeName);
        if(paymentType != null) {
            paymentType.setDeleted(true);
            _paymentTypeRepository.save(paymentType);
        }
    }

    @Override
    public List<RegisterBank> getBanks() {
        List<RegisterBank> registeredBanks = new ArrayList<>();
        for (PaymentType paymentType : getAllActive()) {
            if(paymentType.getBankCode() != null) {
                RegisterBank registerBank = new RegisterBank();
                registerBank.setName(paymentType.getPaymentType());
                registerBank.setBankCode(paymentType.getBankCode());
                registeredBanks.add(registerBank);
            }
        }
        return registeredBanks;
    }

    private List<PaymentType> getAllActive() {
        List<PaymentType> retList = new ArrayList<>();
        for (PaymentType paymentType : _paymentTypeRepository.findAll()) {
            if(!paymentType.isDeleted()) {
                retList.add(paymentType);
            }
        }
        return retList;
    }

    private PaymentType findByPaymentType(String paymentTypeName, String secureVipAddress) {
        System.out.println(paymentTypeName+" : "+secureVipAddress);
        for (PaymentType paymentType : _paymentTypeRepository.findAll()) {
            System.out.println("-------->>>" + paymentType.getPaymentType()+" : "+paymentType.getSecureVipAddress()+" - "+paymentType.isDeleted());
            if(paymentType.getPaymentType().toLowerCase().startsWith(paymentTypeName.toLowerCase().substring(0,3)) &&
                    !paymentType.isDeleted() &&
                            paymentType.getSecureVipAddress().equals(secureVipAddress)) {
                System.out.println("true");
                return paymentType;
            }
        }
        return null;
    }
}
