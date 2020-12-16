package com.payment.unicreditservice.services.implementation;

import com.payment.unicreditservice.dto.request.CardHolderData;
import com.payment.unicreditservice.dto.request.RequestPcc;
import com.payment.unicreditservice.dto.response.ResponsePcc;
import com.payment.unicreditservice.dto.response.TransactionResponse;
import com.payment.unicreditservice.entity.*;
import com.payment.unicreditservice.feign.PccClient;
import com.payment.unicreditservice.repository.*;
import com.payment.unicreditservice.services.definition.ITransactionService;
import com.payment.unicreditservice.util.enums.TransactionStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TransactionService implements ITransactionService {

    private final PasswordEncoder _passwordEncoder;
    private final IPaymentRequestRepository _paymentRequestRepository;
    private final IAccountRepository _accountRepository;
    private final ICustomerAccountRepository _customerAccountRepository;
    private final ITransactionRepository _transactionRepository;
    private final IOrderCounterRepository _orderCounterRepository;
    private final PccClient _pccClient;

    public TransactionService(PasswordEncoder passwordEncoder, IPaymentRequestRepository paymentRequestRepository, IAccountRepository accountRepository, ICustomerAccountRepository customerAccountRepository, ITransactionRepository transactionRepository, IOrderCounterRepository orderCounterRepository, PccClient pccClient) {
        _passwordEncoder = passwordEncoder;
        _paymentRequestRepository = paymentRequestRepository;
        _accountRepository = accountRepository;
        _customerAccountRepository = customerAccountRepository;
        _transactionRepository = transactionRepository;
        _orderCounterRepository = orderCounterRepository;
        _pccClient = pccClient;
    }

    @Override
    public TransactionResponse pay(CardHolderData cardHolderData) throws IllegalAccessException, NoSuchFieldException {
        PaymentRequest paymentRequestOptional = getPaymentRequestFromPaymentId(cardHolderData.getPaymentId());
        PaymentRequest paymentRequest = Optional.ofNullable(paymentRequestOptional).orElseThrow(NoSuchElementException::new);
        Optional<Account> accountOptional = getAccountFromCardHolderData(cardHolderData);

        if(!accountOptional.isPresent()) {
            if (!isBankEquals(cardHolderData, paymentRequest)) {
                OrderCounter orderCounter = createNewOrderCounter();
                RequestPcc requestPcc = createRequestPcc(orderCounter, cardHolderData, paymentRequest.getAmount());
                ResponsePcc responsePcc = _pccClient.sendToPcc(requestPcc);

                paymentRequest.setOrderCounter(orderCounter);
                TransactionStatus transactionStatus = responsePcc != null ? TransactionStatus.SUCCESS : TransactionStatus.ERROR;
                createTransactionFromPcc(paymentRequest, transactionStatus);
            }
        } else if(!isCardHolderDataValid(accountOptional.get(), cardHolderData)) {
            throw new IllegalAccessException();
        } else {
            settlementInSameBank(accountOptional.get(), paymentRequest, paymentRequest.getAmount());
        }

        return mapTransactionToTransactionResponse(paymentRequest);
    }

    private void createTransactionFromPcc(PaymentRequest paymentRequest, TransactionStatus transactionStatus) {
        Transaction transaction = new Transaction();
        transaction.setCustomerAccount(null);
        transaction.setPaymentRequest(paymentRequest);
        transaction.setStatus(transactionStatus);
        _transactionRepository.save(transaction);
    }

    private RequestPcc createRequestPcc(OrderCounter orderCounter, CardHolderData cardHolderData, double amount) {
        RequestPcc requestPcc = new RequestPcc();
        requestPcc.setAccountNumber(cardHolderData.getAccountNumber());
        requestPcc.setAcquirerOrderId(orderCounter.getId());
        requestPcc.setAcquirerOrderTimestamp(orderCounter.getCurrentDateTime().toString());
        requestPcc.setAmount(amount);
        requestPcc.setName(cardHolderData.getCardHolderName());
        requestPcc.setSecurityCode(cardHolderData.getSecurityCode());
        requestPcc.setValidThru(cardHolderData.getValidThru());
        return requestPcc;
    }

    @Override
    public ResponsePcc payPcc(RequestPcc requestPcc) throws IllegalAccessException {
        CardHolderData cardHolderData = createCardHolderDataFromRequestPcc(requestPcc);
        Optional<Account> accountOptional = getAccountFromCardHolderData(cardHolderData);
        Account customer = accountOptional.orElseThrow(IllegalAccessError::new);

        if(!isCardHolderDataValid(customer, cardHolderData)) {
            throw new IllegalAccessException();
        }

        OrderCounter orderCounter = settlementInOneBank(customer, requestPcc.getAmount());
        return mapOrderCounterToResponsePcc(orderCounter, requestPcc);
    }

    private ResponsePcc mapOrderCounterToResponsePcc(OrderCounter orderCounter, RequestPcc requestPcc) {
        if(orderCounter == null) {
            return null;
        }
        ResponsePcc responsePcc = new ResponsePcc();
        responsePcc.setAcquirerOrderId(requestPcc.getAcquirerOrderId());
        responsePcc.setAcquirerOrderTimestamp(requestPcc.getAcquirerOrderTimestamp());
        responsePcc.setIssuerOrderId(orderCounter.getId());
        responsePcc.setIssuerOrderTimestamp(orderCounter.getCurrentDateTime().toString());
        return responsePcc;
    }

    // TODO mora biti thread safe
    private OrderCounter settlementInOneBank(Account customer, double amount) {
        if(customer.getCurrentAmount()-amount < 0) {
            return null;
        } else {
            customer.setCurrentAmount(customer.getCurrentAmount() - amount);
            OrderCounter orderCounter = createNewOrderCounter();
            Optional<CustomerAccount> customerAccount = _customerAccountRepository.findById(customer.getId());
            _accountRepository.save(customerAccount.get());
            return orderCounter;
        }
    }

    private OrderCounter createNewOrderCounter() {
        OrderCounter orderCounter = new OrderCounter();
        orderCounter.setCurrentDateTime(LocalDateTime.now());
        orderCounter.setStatus(TransactionStatus.SUCCESS);
        return _orderCounterRepository.save(orderCounter);
    }

    private CardHolderData createCardHolderDataFromRequestPcc(RequestPcc requestPcc) {
        CardHolderData cardHolderData = new CardHolderData();
        cardHolderData.setCardHolderName(requestPcc.getName());
        cardHolderData.setAccountNumber(requestPcc.getAccountNumber());
        cardHolderData.setSecurityCode(requestPcc.getSecurityCode());
        cardHolderData.setValidThru(requestPcc.getValidThru());
        return cardHolderData;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
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
                .merchantOrderId(String.valueOf(paymentRequest.getMerchantOrder().getId()))
                .paymentId(String.valueOf(paymentRequest.getId()))
                .build();
    }

    // TODO mora biti thread safe
    void settlementInSameBank(Account customer, PaymentRequest paymentRequest, double amount) throws NoSuchFieldException {
        SellerAccount sellerAccount = paymentRequest.getMerchantOrder().getSellerAccount();
        if(customer.getCurrentAmount()-amount < 0) {
            createTransaction(customer, paymentRequest, TransactionStatus.FAIL);
            throw new NoSuchFieldException();
        } else {
            customer.setCurrentAmount(customer.getCurrentAmount() - amount);
            sellerAccount.setCurrentAmount(sellerAccount.getCurrentAmount() + amount);
            _accountRepository.save(customer);
            _accountRepository.save(sellerAccount);
            createTransaction(customer, paymentRequest, TransactionStatus.SUCCESS);
        }
    }

    private void createTransaction(Account customerAccount, PaymentRequest paymentRequest, TransactionStatus transactionStatus) {
        Optional<CustomerAccount> retrievedCustomerAccountOptional = _customerAccountRepository.findById(customerAccount.getId());
        CustomerAccount retrievedCustomerAccount = retrievedCustomerAccountOptional.orElseThrow(NoSuchElementException::new);
        Transaction transaction = new Transaction();
        transaction.setCustomerAccount(retrievedCustomerAccount);
        transaction.setPaymentRequest(paymentRequest);
        transaction.setStatus(transactionStatus);
        _transactionRepository.save(transaction);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Optional<Account> getAccountFromCardHolderData(CardHolderData cardHolderData) {
        return _accountRepository.findAll().stream()
                .filter(acc -> LocalDate.now().isAfter(acc.getDateOpened()) &&
                        acc.getDateClosed() == null &&
                        _passwordEncoder.matches(cardHolderData.getAccountNumber(), acc.getAccountNumber()))

                .findFirst();
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
