package com.payment.bankservice.services.implementation;

import com.payment.bankservice.dto.request.PaymentRequestDTO;
import com.payment.bankservice.dto.response.PaymentRequestStatus;
import com.payment.bankservice.dto.response.PaymentResponse;
import com.payment.bankservice.entity.*;
import com.payment.bankservice.repository.*;
import com.payment.bankservice.services.definition.IPaymentService;
import com.payment.bankservice.util.enums.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class PaymentService implements IPaymentService {

    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private String bankName;

    private final PasswordEncoder _passwordEncoder;
    private final ISellerAccountRepository _sellerAccountRepository;
    private final IMerchantOrderRepository _merchantOrderRepository;
    private final IPaymentRequestRepository _paymentRequestRepository;
    private final IUrlResponderRepository _urlResponderRepository;
    private final ITransactionRepository _transactionRepository;
    private final IBankRepository _bankRepository;

    public PaymentService(PasswordEncoder passwordEncoder, ISellerAccountRepository sellerAccountRepository, IMerchantOrderRepository merchantOrderRepository, IPaymentRequestRepository paymentRequestRepository, IUrlResponderRepository urlResponderRepository, ITransactionRepository transactionRepository, IBankRepository bankRepository) {
        _passwordEncoder = passwordEncoder;
        _sellerAccountRepository = sellerAccountRepository;
        _merchantOrderRepository = merchantOrderRepository;
        _paymentRequestRepository = paymentRequestRepository;
        _urlResponderRepository = urlResponderRepository;
        _transactionRepository = transactionRepository;
        _bankRepository = bankRepository;
    }

    @Override
    public PaymentResponse checkPaymentRequest(PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException {
        logger.info("[{}] check payment request [merchantId={}-]", bankName, paymentRequestDTO.getMerchantId().substring(0, 4));
        if(!isMerchantValid(paymentRequestDTO.getMerchantId(), paymentRequestDTO.getMerchantPassword()) ||
            !isAmountValid(paymentRequestDTO.getAmount())) {
            logger.warn("[{}] payment request invalid [merchantId={}-]", bankName, paymentRequestDTO.getMerchantId().substring(0, 4));
            throw new IllegalAccessException();
        }

        logger.info("[{}] payment request valid [merchantId={}-]", bankName, paymentRequestDTO.getMerchantId().substring(0, 4));
        PaymentRequest storedPaymentRequest = createPaymentRequest(paymentRequestDTO);
        return mapPaymentRequestToPaymentResponse(storedPaymentRequest);
    }

    @Override
    public void cancelRequest(String paymentId, String bankName) {
        PaymentRequest paymentRequest = getPaymentRequestFromPaymentCounterAndBankName(paymentId, bankName);
        if(paymentRequest != null) {
            paymentRequest.setDeleted(true);
            _paymentRequestRepository.save(paymentRequest);
        }
    }

    private PaymentRequest getPaymentRequestFromPaymentCounterAndBankName(String paymentId, String bankName) {
        List<PaymentRequest> paymentRequestList = _paymentRequestRepository.findByPaymentCounter(Integer.parseInt(paymentId))
                .stream()
                .filter(paymentRequest -> !paymentRequest.isDeleted())
                .collect(Collectors.toList());
        return getPaymentRequestForBankName(paymentRequestList, bankName);
    }

    @Override
    public PaymentRequestStatus checkRequestStatus(String paymentId, String bankName) {
        PaymentRequest paymentRequestForBankName = getPaymentRequestFromPaymentCounterAndBankName(paymentId, bankName);
        if(paymentRequestForBankName != null) {
            TransactionStatus transactionStatus = checkTransactionStatusForPayRequest(paymentRequestForBankName);
            if(transactionStatus == null) {
                return createPaymentRequestStatus("PENDING");
            } else {
                return createPaymentRequestStatus(transactionStatus.toString());
            }
        }
        return null;
    }

    private PaymentRequest getPaymentRequestForBankName(List<PaymentRequest> paymentRequestList, String bankName) {
        for (PaymentRequest paymentRequest : paymentRequestList) {
            MerchantOrder merchantOrder = paymentRequest.getMerchantOrder();
            SellerAccount sellerAccount = merchantOrder.getSellerAccount();
            String bankCode = getBankCodeFromBankName(bankName);
            if(sellerAccount.getBankCode().equals(bankCode)) {
                return paymentRequest;
            }
        }
        return null;
    }

    private String getBankCodeFromBankName(String bankName) {
        for (Bank bank: _bankRepository.findAll()) {
            if(bank.getName().toLowerCase().equals(bankName.toLowerCase())) {
                return bank.getBankCode();
            }
        }
        return null;
    }

    private PaymentRequestStatus createPaymentRequestStatus(String status) {
        PaymentRequestStatus paymentRequestStatus = new PaymentRequestStatus();
        paymentRequestStatus.setStatus(status);
        return paymentRequestStatus;
    }

    private TransactionStatus checkTransactionStatusForPayRequest(PaymentRequest paymentRequest) {
        for (Transaction transaction : _transactionRepository.findAll()) {
            if(transaction.getPaymentRequest().getId().equals(paymentRequest.getId())) {
                return transaction.getStatus();
            }
        }
        return null;
    }

    private PaymentRequest createPaymentRequest(PaymentRequestDTO paymentRequestDTO) {
        MerchantOrder savedMerchantOrder = createMerchantOrder(paymentRequestDTO);
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(paymentRequestDTO.getAmount());
        paymentRequest.setMerchantOrder(savedMerchantOrder);
        Bank bank = saveNewPaymentInBank(savedMerchantOrder.getSellerAccount().getBankCode());
        paymentRequest.setPaymentCounter(bank.getPaymentCounter());
        logger.info("[{}] create paymentId [merchantId={}-]", bankName, paymentRequestDTO.getMerchantId().substring(0, 4));
        return _paymentRequestRepository.save(paymentRequest);
    }

    // TODO pessimistic lock
    private Bank saveNewPaymentInBank(String bankCode) {
        Bank bank = _bankRepository.findByBankCode(bankCode);
        bank.setPaymentCounter(bank.getPaymentCounter() + 1);
        return _bankRepository.save(bank);
    }

    private MerchantOrder createMerchantOrder(PaymentRequestDTO paymentRequestDTO) {
        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder.setDateOpened(LocalDateTime.now());
        Optional<SellerAccount> sellerAccount = getMerchantById(paymentRequestDTO.getMerchantId(), paymentRequestDTO.getMerchantPassword());
        sellerAccount.ifPresent(merchantOrder::setSellerAccount);
        merchantOrder.setCounter(getNextMerchantNumber(sellerAccount));
        logger.info("[{}] create merchantOrder [merchantId={}-]", bankName, paymentRequestDTO.getMerchantId().substring(0, 4));
        return _merchantOrderRepository.save(merchantOrder);
    }

    // TODO pessimistic lock
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private int getNextMerchantNumber(Optional<SellerAccount> sellerAccount) {
        if(sellerAccount.isPresent()) {
            Set<MerchantOrder> merchantOrdersFromBank = _merchantOrderRepository.findAllBySellerAccount(sellerAccount.get());
            int currentCounter = getMaxMerchantOrder(merchantOrdersFromBank);
            return currentCounter + 1;
        }
        return -1;
    }

    private int getMaxMerchantOrder(Set<MerchantOrder> merchantOrdersFromBank) {
        List<Integer> countersFromBank = new ArrayList<>();
        for (MerchantOrder order : merchantOrdersFromBank) {
            countersFromBank.add(order.getCounter());
        }
        return getMaxNumber(countersFromBank);
    }

    private int getMaxNumber(List<Integer> listIntegers) {
        return listIntegers.stream()
                .mapToInt(v -> v)
                .max().orElse(0);
    }

    private PaymentResponse mapPaymentRequestToPaymentResponse(PaymentRequest paymentRequest) {
        return PaymentResponse.builder().
                paymentId(String.valueOf(getLastPaymentCounterFromBank(paymentRequest.getMerchantOrder().getSellerAccount().getBankCode()))).
                paymentUrl(getLatestPaymentUrl(paymentRequest.getMerchantOrder().getSellerAccount().getBankCode())).
                build();
    }

    private int getLastPaymentCounterFromBank(String bankCode) {
        Bank bank = _bankRepository.findByBankCode(bankCode);
        return bank.getPaymentCounter();
    }

    private String getLatestPaymentUrl(String bankCode) {
        String bankName = getBankNameFromBankCode(bankCode);
        List<UrlResponder> urls = _urlResponderRepository.findAll();
        for (UrlResponder url : urls) {
            if(url.getDateClosed() == null &&
                bankName != null &&
                    url.getPaymentUrl().contains(getNewBankName(bankName))) {
                return url.getPaymentUrl();
            }
        }
        return null;
    }

    @SuppressWarnings("SpellCheckingInspection")
    private String getNewBankName(String bankName) {
        if(bankName.equals("bank")) {
            return "unicredit";
        } else if(bankName.equals("bank1")) {
            return "raiffeisen";
        } else {
            return "";
        }
    }

    private String getBankNameFromBankCode(String bankCode) {
        Bank bank = _bankRepository.findByBankCode(bankCode);
        if(bank != null) {
            return bank.getName();
        }
        return null;
    }

    private boolean isAmountValid(double amount) {
        return amount > 0 && amount < Double.MAX_VALUE;
    }

    private boolean isMerchantValid(String merchantId, String merchantPassword) {
        Optional<SellerAccount> stored_merchant = getMerchantById(merchantId, merchantPassword);
        return stored_merchant.isPresent();
    }

    private Optional<SellerAccount> getMerchantById(String merchantId, String merchantPassword) {
        return _sellerAccountRepository.findAll().stream()
                .filter(seller -> seller.getDateClosed() == null &&
                        _passwordEncoder.matches(merchantId, seller.getMerchantId()) &&
                        _passwordEncoder.matches(merchantPassword, seller.getMerchantPassword()))
                .findFirst();
    }

}
