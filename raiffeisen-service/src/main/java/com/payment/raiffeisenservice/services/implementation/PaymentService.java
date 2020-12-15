package com.payment.raiffeisenservice.services.implementation;

import com.payment.raiffeisenservice.dto.request.PaymentRequestDTO;
import com.payment.raiffeisenservice.dto.response.PaymentResponse;
import com.payment.raiffeisenservice.entity.*;
import com.payment.raiffeisenservice.repository.*;
import com.payment.raiffeisenservice.services.definition.IPaymentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class PaymentService implements IPaymentService {

    private final PasswordEncoder _passwordEncoder;
    private final ISellerAccountRepository _sellerAccountRepository;
    private final IMerchantOrderRepository _merchantOrderRepository;
    private final IPaymentRequestRepository _paymentRequestRepository;
    private final IUrlResponderRepository _urlResponderRepository;
    private final IOrderCounterRepository _orderCounterRepository;

    public PaymentService(PasswordEncoder passwordEncoder, ISellerAccountRepository sellerAccountRepository, IMerchantOrderRepository merchantOrderRepository, IPaymentRequestRepository paymentRequestRepository, IUrlResponderRepository urlResponderRepository, IOrderCounterRepository orderCounterRepository) {
        _passwordEncoder = passwordEncoder;
        _sellerAccountRepository = sellerAccountRepository;
        _merchantOrderRepository = merchantOrderRepository;
        _paymentRequestRepository = paymentRequestRepository;
        _urlResponderRepository = urlResponderRepository;
        _orderCounterRepository = orderCounterRepository;
    }

    @Override
    public PaymentResponse checkPaymentRequest(PaymentRequestDTO paymentRequestDTO) throws IllegalAccessException {
        if(!isMerchantValid(paymentRequestDTO.getMerchantId(), paymentRequestDTO.getMerchantPassword()) ||
            !isAmountValid(paymentRequestDTO.getAmount())) {
            throw new IllegalAccessException();
        }

        PaymentRequest storedPaymentRequest = createPaymentRequest(paymentRequestDTO);
        return mapPaymentRequestToPaymentResponse(storedPaymentRequest);
    }

    private PaymentRequest createPaymentRequest(PaymentRequestDTO paymentRequestDTO) {
        MerchantOrder savedMerchantOrder = createMerchantOrder(paymentRequestDTO);
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(paymentRequestDTO.getAmount());
        paymentRequest.setMerchantOrder(savedMerchantOrder);
        return _paymentRequestRepository.save(paymentRequest);
    }

    private OrderCounter createOrderCounter() {
        OrderCounter orderCounter = new OrderCounter();
        orderCounter.setCurrentDateTime(LocalDateTime.now());
        return _orderCounterRepository.save(orderCounter);
    }

    private MerchantOrder createMerchantOrder(PaymentRequestDTO paymentRequestDTO) {
        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder.setDateOpened(LocalDateTime.now());
        Optional<SellerAccount> sellerAccount = getMerchantById(paymentRequestDTO.getMerchantId(), paymentRequestDTO.getMerchantPassword());
        sellerAccount.ifPresent(merchantOrder::setSellerAccount);
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
