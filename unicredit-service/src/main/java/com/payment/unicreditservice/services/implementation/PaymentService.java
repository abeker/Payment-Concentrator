package com.payment.unicreditservice.services.implementation;

import com.payment.unicreditservice.dto.request.PaymentRequestDTO;
import com.payment.unicreditservice.dto.response.PaymentRequestStatus;
import com.payment.unicreditservice.dto.response.PaymentResponse;
import com.payment.unicreditservice.entity.*;
import com.payment.unicreditservice.repository.*;
import com.payment.unicreditservice.services.definition.IPaymentService;
import com.payment.unicreditservice.util.enums.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class PaymentService implements IPaymentService {

    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @Value("${bank.name}")
    private String bankName;

    private final PasswordEncoder _passwordEncoder;
    private final ISellerAccountRepository _sellerAccountRepository;
    private final IMerchantOrderRepository _merchantOrderRepository;
    private final IPaymentRequestRepository _paymentRequestRepository;
    private final IUrlResponderRepository _urlResponderRepository;
    private final ITransactionRepository _transactionRepository;

    public PaymentService(PasswordEncoder passwordEncoder, ISellerAccountRepository sellerAccountRepository, IMerchantOrderRepository merchantOrderRepository, IPaymentRequestRepository paymentRequestRepository, IUrlResponderRepository urlResponderRepository, ITransactionRepository transactionRepository) {
        _passwordEncoder = passwordEncoder;
        _sellerAccountRepository = sellerAccountRepository;
        _merchantOrderRepository = merchantOrderRepository;
        _paymentRequestRepository = paymentRequestRepository;
        _urlResponderRepository = urlResponderRepository;
        _transactionRepository = transactionRepository;
    }

    @Override
    public PaymentResponse checkPaymentRequest(PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException {
        System.out.println("MERCHANT ID: " + paymentRequestDTO.getMerchantId() + " /// "  + paymentRequestDTO.getMerchantPassword() + "   " + paymentRequestDTO.getAmount());
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
    public void cancelRequest(String paymentId) {
        Optional<PaymentRequest> paymentRequestOptional = _paymentRequestRepository.findById(Integer.parseInt(paymentId));
        if(paymentRequestOptional.isPresent()) {
            PaymentRequest paymentRequest = paymentRequestOptional.get();
            paymentRequest.setDeleted(true);
            _paymentRequestRepository.save(paymentRequest);
        }
    }

    @Override
    public PaymentRequestStatus checkRequestStatus(String paymentId) {
        Optional<PaymentRequest> paymentRequest = _paymentRequestRepository.findById(Integer.parseInt(paymentId));
        if(paymentRequest.isPresent()) {
            TransactionStatus transactionStatus = checkTransactionStatusForPayRequest(paymentRequest.get());
            if(transactionStatus == null) {
                return createPaymentRequestStatus("PENDING");
            } else {
                return createPaymentRequestStatus(transactionStatus.toString());
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
            if(transaction.getPaymentRequest().getId() == paymentRequest.getId()) {
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
        logger.info("[{}] create paymentId [merchantId={}-]", bankName, paymentRequestDTO.getMerchantId().substring(0, 4));
        return _paymentRequestRepository.save(paymentRequest);
    }

    private MerchantOrder createMerchantOrder(PaymentRequestDTO paymentRequestDTO) {
        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder.setDateOpened(LocalDateTime.now());
        Optional<SellerAccount> sellerAccount = getMerchantById(paymentRequestDTO.getMerchantId(), paymentRequestDTO.getMerchantPassword());
        sellerAccount.ifPresent(merchantOrder::setSellerAccount);
        logger.info("[{}] create merchantOrder [merchantId={}-]", bankName, paymentRequestDTO.getMerchantId().substring(0, 4));
        return _merchantOrderRepository.save(merchantOrder);
    }

    private PaymentResponse mapPaymentRequestToPaymentResponse(PaymentRequest paymentRequest) {
        return PaymentResponse.builder().
                paymentId(String.valueOf(paymentRequest.getId())).
                paymentUrl(getLatestPaymentUrl()).
                build();
    }

    private String getLatestPaymentUrl() {
        List<UrlResponder> urls = _urlResponderRepository.findAll();
        for (UrlResponder url : urls) {
            if(url.getDateClosed() == null) {
                return url.getPaymentUrl();
            }
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
