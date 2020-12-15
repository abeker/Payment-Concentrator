package com.payment.raiffeisenservice.services.implementation;

import com.payment.raiffeisenservice.dto.request.CardHolderData;
import com.payment.raiffeisenservice.dto.response.TransactionResponse;
import com.payment.raiffeisenservice.entity.*;
import com.payment.raiffeisenservice.repository.IAccountRepository;
import com.payment.raiffeisenservice.repository.ICustomerAccountRepository;
import com.payment.raiffeisenservice.repository.IPaymentRequestRepository;
import com.payment.raiffeisenservice.services.definition.ITransactionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional(readOnly = true)
public class TransactionService implements ITransactionService {

    private final PasswordEncoder _passwordEncoder;
    private final IPaymentRequestRepository _paymentRequestRepository;
    private final IAccountRepository _accountRepository;
    private final ICustomerAccountRepository _customerAccountRepository;

    public TransactionService(PasswordEncoder passwordEncoder, IPaymentRequestRepository paymentRequestRepository, IAccountRepository accountRepository, ICustomerAccountRepository customerAccountRepository) {
        _passwordEncoder = passwordEncoder;
        _paymentRequestRepository = paymentRequestRepository;
        _accountRepository = accountRepository;
        _customerAccountRepository = customerAccountRepository;
    }

    @Override
    public TransactionResponse pay(CardHolderData cardHolderData) throws IllegalAccessException, NoSuchFieldException {
        PaymentRequest paymentRequestOptional = getPaymentRequestFromPaymentId(cardHolderData.getPaymentId());
        PaymentRequest paymentRequest = Optional.ofNullable(paymentRequestOptional).orElseThrow(NoSuchElementException::new);
        Account accountOptional = getAccountFromCardHolderData(cardHolderData);
        Account customer = Optional.of(accountOptional).orElseThrow(IllegalAccessError::new);

        if(!isCardHolderDataValid(customer, cardHolderData)) {
            throw new IllegalAccessException();
        } else {
            if(!isBankEquals(cardHolderData, paymentRequest)) {
                // TODO pozvati pcc
                throw new IllegalAccessException();
            }
        }

        settlementInSameBank(customer, paymentRequest.getMerchantOrder().getSellerAccount(), paymentRequest.getAmount());
        return mapTransactionToTransactionResponse(paymentRequest);
    }

    private boolean isCardHolderDataValid(Account account, CardHolderData cardHolderData) {
        Optional<CustomerAccount> customerAccountOptional = _customerAccountRepository.findById(account.getId());
        CustomerAccount customerAccount = customerAccountOptional.orElseThrow(NoSuchElementException::new);
        return _passwordEncoder.matches(cardHolderData.getAccountNumber(), customerAccount.getAccountNumber()) &&
                customerAccount.getName().equals(cardHolderData.getCardHolderName()) &&
                _passwordEncoder.matches(cardHolderData.getSecurityCode(), customerAccount.getSecurityCode()) &&
                customerAccount.getValidThru().equals(cardHolderData.getValidThru());
    }

    private TransactionResponse mapTransactionToTransactionResponse(PaymentRequest paymentRequest) {
        String acquirerOrderId = paymentRequest.getOrderCounter() != null ? String.valueOf(paymentRequest.getOrderCounter().getId()) : null;
        String acquirerOrderTimestamp = paymentRequest.getOrderCounter() != null ? paymentRequest.getOrderCounter().getCurrentDateTime().toString() : null;
        return TransactionResponse.builder()
                .acquirerOrderId(acquirerOrderId)
                .acquirerTimestamp(acquirerOrderTimestamp)
                .merchantOrderId(paymentRequest.getMerchantOrder().getSellerAccount().getId().toString())
                .paymentId(String.valueOf(paymentRequest.getId()))
                .build();
    }

    ReentrantLock lock = new ReentrantLock();
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void settlementInSameBank(Account customer, SellerAccount sellerAccount, double amount) throws NoSuchFieldException {
        if(customer.getCurrentAmount()-amount < 0) {
            throw new NoSuchFieldException();
        } else {
            lock.lock();
            try {
                customer.setCurrentAmount(customer.getCurrentAmount() - amount);
                sellerAccount.setCurrentAmount(sellerAccount.getCurrentAmount() + amount);
                _accountRepository.save(customer);
                _accountRepository.save(sellerAccount);
            } finally {
                lock.unlock();
            }
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Account getAccountFromCardHolderData(CardHolderData cardHolderData) {
        return _accountRepository.findAll().stream()
                .filter(acc -> LocalDate.now().isAfter(acc.getDateOpened()) &&
                        acc.getDateClosed() == null &&
                        _passwordEncoder.matches(cardHolderData.getAccountNumber(), acc.getAccountNumber()))

                .findFirst().get();
    }

    private boolean isBankEquals(CardHolderData cardHolderData, PaymentRequest paymentRequest) {
        String merchantBankCode = getMerchantBankCode(paymentRequest);
        String customerBankCode = cardHolderData.getAccountNumber().substring(1, 6);
        return customerBankCode.equals(merchantBankCode);
    }

    private String getMerchantBankCode(PaymentRequest paymentRequest) {
        MerchantOrder merchantOrder = paymentRequest.getMerchantOrder();
        SellerAccount sellerAccount = merchantOrder.getSellerAccount();
        return sellerAccount.getBankCode();
    }

    private PaymentRequest getPaymentRequestFromPaymentId(String paymentId) {
        Optional<PaymentRequest> paymentRequestOptional = _paymentRequestRepository.findById(Integer.parseInt(paymentId));
        if(paymentRequestOptional.isPresent() &&
            !paymentRequestOptional.get().isDeleted()) {
            return paymentRequestOptional.get();
        }
        return null;
    }

}
