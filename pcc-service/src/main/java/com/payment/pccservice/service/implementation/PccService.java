package com.payment.pccservice.service.implementation;

import com.payment.pccservice.dto.request.RequestPcc;
import com.payment.pccservice.dto.response.ResponsePcc;
import com.payment.pccservice.entity.Bank;
import com.payment.pccservice.feign.BankClient;
import com.payment.pccservice.repository.IBankRepository;
import com.payment.pccservice.service.definition.IPccService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class PccService implements IPccService {

    private final Logger logger = LoggerFactory.getLogger(PccService.class);
    private String pccName = "PCC";

    private final IBankRepository _bankRepository;
    private final BankClient _bankClient;

    public PccService(IBankRepository bankRepository, BankClient unicreditClient) {
        _bankRepository = bankRepository;
        _bankClient = unicreditClient;
    }

    @Override
    public ResponsePcc forwardRequest(RequestPcc requestPcc) {
        String bankCode = getBankCodeFromAccountNumber(requestPcc.getAccountNumber());
        Bank bank = _bankRepository.findByBankCode(bankCode);
        ResponsePcc responsePcc;
//        if(bank.getBankName().equals("unicredit")) {
//            logger.info("[{}] pcc request forwarding [from={}], [to={}], [acq-orderId={}]", pccName, "Unicredit Bank", "Raiffeisen Bank", requestPcc.getAcquirerOrderId());
//            responsePcc = _bankClient.sendToClient(requestPcc);
//        } else {
//            logger.info("[{}] pcc request forwarding [from={}], [to={}], [acq-orderId={}]", pccName, "Unicredit Bank", "Raiffeisen Bank", requestPcc.getAcquirerOrderId());
//            responsePcc = _bankClient.sendToClient(requestPcc);
//        }

        logger.info("[{}] pcc request forwarding [from={}], [to={}], [acq-orderId={}]", pccName, "Bank", "Bank", requestPcc.getAcquirerOrderId());
        responsePcc = _bankClient.sendToClient(requestPcc);

        return responsePcc;
    }

    private String getBankCodeFromAccountNumber(String accountNumber) {
        return accountNumber.substring(1,6);
    }
}
