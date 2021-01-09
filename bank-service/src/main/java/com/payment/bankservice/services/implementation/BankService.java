package com.payment.bankservice.services.implementation;

import com.payment.bankservice.dto.response.BankResponse;
import com.payment.bankservice.dto.response.RegisterBank;
import com.payment.bankservice.entity.Bank;
import com.payment.bankservice.repository.IBankRepository;
import com.payment.bankservice.services.definition.IBankService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService implements IBankService {

    private final IBankRepository _bankRepository;

    public BankService(IBankRepository _bankRepository) {
        this._bankRepository = _bankRepository;
    }

    @Override
    public void registerNewBank(RegisterBank registerBank) {
        Bank bank = _bankRepository.findByBankCode(registerBank.getBankCode());
        if(bank != null) {
            bank.setName(registerBank.getName());
            _bankRepository.save(bank);
        }
    }

    @Override
    public void registerBanks(List<RegisterBank> registeredBanks) {
        if(!registeredBanks.isEmpty()) {
            for (RegisterBank registerBank : registeredBanks) {
                registerNewBank(registerBank);
            }
        }
    }

    @Override
    public List<BankResponse> getBanks() {
        List<Bank> existedBanks = _bankRepository.findAll()
                .stream()
                .filter(bank -> !bank.isDeleted())
                .collect(Collectors.toList());
        List<BankResponse> retList = new ArrayList<>();
        for (Bank bank : existedBanks) {
            retList.add(createBankResponseFromBank(bank));
        }
        return retList;
    }

    private BankResponse createBankResponseFromBank(Bank bank) {
        BankResponse bankResponse = new BankResponse();
        bankResponse.setId(bank.getId().toString());
        bankResponse.setBankCode(bank.getBankCode());
        bankResponse.setName(bank.getName());
        return bankResponse;
    }
}
