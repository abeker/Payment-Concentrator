package com.payment.bankservice.services.definition;

import com.payment.bankservice.dto.response.BankResponse;
import com.payment.bankservice.dto.response.RegisterBank;

import java.util.List;

public interface IBankService {

    void registerNewBank(RegisterBank registerBank);

    void registerBanks(List<RegisterBank> registeredBanks);

    List<BankResponse> getBanks();
}
