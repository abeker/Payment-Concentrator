package com.payment.pccservice.service.implementation;

import com.payment.pccservice.dto.request.RequestPcc;
import com.payment.pccservice.dto.response.ResponsePcc;
import com.payment.pccservice.entity.Bank;
import com.payment.pccservice.feign.RaiffeisenClient;
import com.payment.pccservice.feign.UnicreditClient;
import com.payment.pccservice.repository.IBankRepository;
import com.payment.pccservice.service.definition.IPccService;
import org.springframework.stereotype.Service;

@Service
public class PccService implements IPccService {

    private final IBankRepository _bankRepository;
    private final RaiffeisenClient _raiffeisenClient;
    private final UnicreditClient _unicreditClient;

    public PccService(IBankRepository bankRepository, RaiffeisenClient raiffeisenClient, UnicreditClient unicreditClient) {
        _bankRepository = bankRepository;
        _raiffeisenClient = raiffeisenClient;
        _unicreditClient = unicreditClient;
    }

    @Override
    public ResponsePcc forwardRequest(RequestPcc requestPcc) {
        String bankCode = getBankCodeFromAccountNumber(requestPcc.getAccountNumber());
        Bank bank = _bankRepository.findByBankCode(bankCode);
        ResponsePcc responsePcc;
        if(bank.getBankName().equals("unicredit")) {
            responsePcc = _unicreditClient.sendToClient(requestPcc);
        } else {
            responsePcc = _raiffeisenClient.sendToClient(requestPcc);
        }

        return responsePcc;
    }

    private String getBankCodeFromAccountNumber(String accountNumber) {
        return accountNumber.substring(1,6);
    }
}
