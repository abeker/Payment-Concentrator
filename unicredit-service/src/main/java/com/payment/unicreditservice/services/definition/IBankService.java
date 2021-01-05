package com.payment.unicreditservice.services.definition;

import com.payment.unicreditservice.dto.response.RegisterBank;

import java.util.List;

public interface IBankService {

    void registerNewBank(RegisterBank registerBank);

    void registerBanks(List<RegisterBank> registeredBanks);
}
