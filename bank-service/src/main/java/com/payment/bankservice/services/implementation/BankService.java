package com.payment.bankservice.services.implementation;

import com.payment.bankservice.dto.response.RegisterBank;
import com.payment.bankservice.entity.Bank;
import com.payment.bankservice.repository.IBankRepository;
import com.payment.bankservice.services.definition.IBankService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
