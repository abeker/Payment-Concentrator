package com.payment.bankservice.services.implementation;

import com.payment.bankservice.dto.request.CardHolderData;
import com.payment.bankservice.dto.request.RequestPcc;
import com.payment.bankservice.dto.response.ResponsePcc;
import com.payment.bankservice.dto.response.TransactionResponse;
import com.payment.bankservice.entity.*;
import com.payment.bankservice.feign.PccClient;
import com.payment.bankservice.repository.*;
import com.payment.bankservice.services.definition.ITransactionService;
import com.payment.bankservice.util.enums.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class TransactionService implements ITransactionService {

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private String bankName;

    private final PasswordEncoder _passwordEncoder;
    private final IPaymentRequestRepository _paymentRequestRepository;
    private final IAccountRepository _accountRepository;
    private final ICustomerAccountRepository _customerAccountRepository;
    private final ITransactionRepository _transactionRepository;
    private final IOrderCounterRepository _orderCounterRepository;
    private final IBankRepository _bankRepository;
    private final PccClient _pccClient;

    public TransactionService(PasswordEncoder passwordEncoder, IPaymentRequestRepository paymentRequestRepository, IAccountRepository accountRepository, ICustomerAccountRepository customerAccountRepository, ITransactionRepository transactionRepository, IOrderCounterRepository orderCounterRepository, IBankRepository bankRepository, PccClient pccClient) {
        _passwordEncoder = passwordEncoder;
        _paymentRequestRepository = paymentRequestRepository;
        _accountRepository = accountRepository;
        _customerAccountRepository = customerAccountRepository;
        _transactionRepository = transactionRepository;
        _orderCounterRepository = orderCounterRepository;
        _bankRepository = bankRepository;
        _pccClient = pccClient;
    }

    @Override
    public TransactionResponse pay(CardHolderData cardHolderData, String bankName) throws IllegalAccessException, NoSuchFieldException {
        this.bankName = bankName;
        String cardholderName = cardHolderData.getCardHolderName();
        logger.info("[{}] payment proccess [{}]", bankName, cardholderName);
        PaymentRequest paymentRequestOptional = getPaymentRequestFromPaymentCounter(cardHolderData.getPaymentId(), bankName);
        PaymentRequest paymentRequest = Optional.ofNullable(paymentRequestOptional).orElseThrow(NoSuchElementException::new);
        Optional<Account> accountOptional = getAccountFromCardHolderData(cardHolderData, bankName);

        if(isPaymentRequestProcessed(paymentRequest)) {
            logger.warn("[{}] request already processed [{}]", bankName, paymentRequest.getId());
            throw new IllegalAccessException();
        } else {
            if (!accountOptional.isPresent()) {
                logger.warn("[{}] account not found [{}]", bankName, cardholderName);
                if (!isBankEquals(cardHolderData, paymentRequest)) {
                    OrderCounter orderCounter = createNewOrderCounter(cardHolderData.getAccountNumber(), bankName, false);
                    logger.warn("[{}] cross bank transaction [{}],[acq-orderId={}]", bankName, cardholderName, orderCounter.getCounter());
                    RequestPcc requestPcc = createRequestPcc(orderCounter, cardHolderData, paymentRequest.getAmount());
                    ResponsePcc responsePcc = _pccClient.sendToPcc(requestPcc);

                    // nema vise para na racunu od klijenta
                    if(responsePcc == null) {
                        createTransactionAfterPcc(TransactionStatus.ERROR, cardholderName, paymentRequest);
                        throw new NoSuchElementException();
                    } else {
                        addMoneyOnMerchantAccount(responsePcc, cardHolderData, paymentRequest);
                        paymentRequest.setOrderCounter(orderCounter);
                        createTransactionAfterPcc(TransactionStatus.SUCCESS, cardholderName, paymentRequest);
                    }
                }
            } else if (!isCardHolderDataValid(accountOptional.get(), cardHolderData)) {
                logger.warn("[{}] invalid cardholder data [{}]", bankName, cardholderName);
                throw new IllegalAccessException();
            } else {
                settlementInSameBank(accountOptional.get(), paymentRequest, paymentRequest.getAmount());
            }
        }

        return mapTransactionToTransactionResponse(paymentRequest);
    }

    private void createTransactionAfterPcc(TransactionStatus transactionStatus, String cardholderName, PaymentRequest paymentRequest) {
        logger.warn("[{}] account not found [{}]", bankName, cardholderName);
        createTransactionFromPcc(paymentRequest, transactionStatus);
    }

    private boolean isPaymentRequestProcessed(PaymentRequest paymentRequest) {
        for (Transaction transaction : _transactionRepository.findAll()) {
            if(transaction.getPaymentRequest().equals(paymentRequest)) {
                return true;
            }
        }
        return false;
    }

    private void addMoneyOnMerchantAccount(ResponsePcc responsePcc, CardHolderData cardHolderData, PaymentRequest paymentRequest) {
        MerchantOrder merchantOrder = paymentRequest.getMerchantOrder();
        SellerAccount sellerAccount = merchantOrder.getSellerAccount();
        Account merchantAccount = _accountRepository.findByAccountNumber(sellerAccount.getAccountNumber());
        if(responsePcc != null &&
                merchantAccount != null) {
            merchantAccount.setCurrentAmount(merchantAccount.getCurrentAmount() + paymentRequest.getAmount());
        }
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
        requestPcc.setAcquirerOrderId(getLastOrderCounterByBankCode(orderCounter.getBankCode()));
        requestPcc.setAcquirerOrderTimestamp(orderCounter.getCurrentDateTime().toString());
        requestPcc.setAmount(amount);
        requestPcc.setName(cardHolderData.getCardHolderName());
        requestPcc.setSecurityCode(cardHolderData.getSecurityCode());
        requestPcc.setValidThru(cardHolderData.getValidThru());
        return requestPcc;
    }

    @Override
    public ResponsePcc payPcc(RequestPcc requestPcc) throws IllegalAccessException {
        logger.info("[{}] payment pcc request [acq-orderId={}]", bankName, requestPcc.getAcquirerOrderId());
        CardHolderData cardHolderData = createCardHolderDataFromRequestPcc(requestPcc);
        Optional<Account> accountOptional = getAccountFromCardHolderData(cardHolderData, getBankNameFromBankCode(requestPcc.getAccountNumber().substring(1,6)));
        Account customer = accountOptional.orElseThrow(IllegalAccessError::new);

        if(!isCardHolderDataValid(customer, cardHolderData)) {
            logger.warn("[{}] illegal cardholder data [acq-orderId={}]", bankName, requestPcc.getAcquirerOrderId());
            throw new IllegalAccessException();
        }

        OrderCounter orderCounter = settlementInOneBank(customer, requestPcc.getAmount(), requestPcc.getAccountNumber());
        logger.info("[{}] payment pcc successfull [acq-orderId={}]", bankName, requestPcc.getAcquirerOrderId());
        return mapOrderCounterToResponsePcc(orderCounter, requestPcc);
    }

    private ResponsePcc mapOrderCounterToResponsePcc(OrderCounter orderCounter, RequestPcc requestPcc) {
        if(orderCounter == null) {
            return null;
        }
        ResponsePcc responsePcc = new ResponsePcc();
        responsePcc.setAcquirerOrderId(requestPcc.getAcquirerOrderId());
        responsePcc.setAcquirerOrderTimestamp(requestPcc.getAcquirerOrderTimestamp());
        responsePcc.setIssuerOrderId(orderCounter.getCounter());
        responsePcc.setIssuerOrderTimestamp(orderCounter.getCurrentDateTime().toString());
        return responsePcc;
    }

    private int getLastOrderCounterByBankCode(String bankCode) {
        Bank bank = _bankRepository.findByBankCode(bankCode);
        return bank.getOrderCounterAcquirer();
    }

    ReentrantLock lockOneBank = new ReentrantLock();
    private OrderCounter settlementInOneBank(Account customer, double amount, String accountNumber) {
        if(customer.getCurrentAmount()-amount < 0) {
            logger.warn("[{}] customer not have enough [name={}], [amount={}]", bankName, customer.getName(), amount);
            return null;
        } else {
            lockOneBank.lock();
            try {
                logger.info("[{}] customer account reducing [name={}], [amount={}]", bankName, customer.getName(), amount);
                customer.setCurrentAmount(customer.getCurrentAmount() - amount);
                Optional<CustomerAccount> customerAccount = _customerAccountRepository.findById(customer.getId());
                _accountRepository.save(customerAccount.get());
            } finally {
                lockOneBank.unlock();
            }
            return createNewOrderCounter(accountNumber, getBankNameFromBankCode(customer.getBankCode()), true);
        }
    }

    private OrderCounter createNewOrderCounter(String accountNumber, String bankName, boolean isPcc) {
        OrderCounter orderCounter = new OrderCounter();
        String bankCode = accountNumber.substring(1,6);
        orderCounter.setBankCode(bankCode);
        Bank updatedBank;
        if(isPcc) {
            updatedBank = saveNewOrderCounterIssuerInBank(getBankCodeFromBankName(bankName));
            orderCounter.setCounter(updatedBank.getOrderCounterIssuer());
        } else {
            updatedBank = saveNewOrderCounterInBank(getBankCodeFromBankName(bankName));
            orderCounter.setCounter(updatedBank.getOrderCounterAcquirer());
        }
        orderCounter.setCurrentDateTime(LocalDateTime.now());
        orderCounter.setStatus(TransactionStatus.SUCCESS);
        return _orderCounterRepository.save(orderCounter);
    }

    private String getBankCodeFromBankName(String bankName) {
        Bank bank = _bankRepository.findByName(bankName);
        return bank.getBankCode();
    }

    // TODO pessimistic lock
    private Bank saveNewOrderCounterIssuerInBank(String bankCode) {
        Bank bank = _bankRepository.findByBankCode(bankCode);
        bank.setOrderCounterIssuer(bank.getOrderCounterIssuer() + 1);
        return _bankRepository.save(bank);
    }

    // TODO pessimistic lock
    private Bank saveNewOrderCounterInBank(String bankCode) {
        Bank bank = _bankRepository.findByBankCode(bankCode);
        bank.setOrderCounterAcquirer(bank.getOrderCounterAcquirer() + 1);
        return _bankRepository.save(bank);
    }

//    // TODO pessimistic lock
//    private int getNextOrderCounter(String bankCode) {
//        List<Integer> countersForOneBank = new ArrayList<>();
//        for (OrderCounter orderCounter : _orderCounterRepository.findAll()) {
//            countersForOneBank.add(orderCounter.getCounter());
//        }
//        int maxCounter = getMaxNumber(countersForOneBank);
//        return maxCounter + 1;
//    }
//
//    private int getMaxNumber(List<Integer> listIntegers) {
//        return listIntegers.stream()
//                .mapToInt(v -> v)
//                .max().orElseThrow(NoSuchElementException::new);
//    }

    private CardHolderData createCardHolderDataFromRequestPcc(RequestPcc requestPcc) {
        CardHolderData cardHolderData = new CardHolderData();
        cardHolderData.setCardHolderName(requestPcc.getName());
        cardHolderData.setAccountNumber(requestPcc.getAccountNumber());
        cardHolderData.setSecurityCode(requestPcc.getSecurityCode());
        cardHolderData.setValidThru(requestPcc.getValidThru());
        return cardHolderData;
    }

    private String getBankNameFromBankCode(String bankCode) {
        Bank bank = _bankRepository.findByBankCode(bankCode);
        if(bank != null) {
            return bank.getName();
        }
        return null;
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
        String acquirerOrderId = paymentRequest.getOrderCounter() != null ? String.valueOf(paymentRequest.getOrderCounter().getCounter()) : null;
        String acquirerOrderTimestamp = paymentRequest.getOrderCounter() != null ? paymentRequest.getOrderCounter().getCurrentDateTime().toString() : null;
        return TransactionResponse.builder()
                .acquirerOrderId(acquirerOrderId)
                .acquirerTimestamp(acquirerOrderTimestamp)
                .merchantOrderId(String.valueOf(paymentRequest.getMerchantOrder().getCounter()))
                .paymentId(String.valueOf(paymentRequest.getPaymentCounter()))
                .build();
    }

    ReentrantLock lockSameBank = new ReentrantLock();
    void settlementInSameBank(Account customer, PaymentRequest paymentRequest, double amount) throws NoSuchFieldException {
        MerchantOrder merchantOrder = paymentRequest.getMerchantOrder();
        SellerAccount sellerAccount = merchantOrder.getSellerAccount();
        logger.info("[{}] one bank settlement [from={}], [to={}], [amount={}]", bankName, customer.getName(), sellerAccount.getName(), amount);
        if(customer.getCurrentAmount()-amount < 0) {
            logger.warn("[{}] customer not have enough [name={}], [amount={}]", bankName, customer.getName(), amount);
            createTransaction(customer, paymentRequest, TransactionStatus.FAIL);
            throw new NoSuchFieldException();
        } else {
            lockSameBank.lock();
            try {
                logger.info("[{}] customer account reducing [name={}], [amount={}]", bankName, customer.getName(), amount);
                customer.setCurrentAmount(customer.getCurrentAmount() - amount);
                sellerAccount.setCurrentAmount(sellerAccount.getCurrentAmount() + amount);
                _accountRepository.save(customer);
                _accountRepository.save(sellerAccount);
                createTransaction(customer, paymentRequest, TransactionStatus.SUCCESS);
            } finally {
                lockSameBank.unlock();
            }
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

    private Optional<Account> getAccountFromCardHolderData(CardHolderData cardHolderData, String bankName) {
        return _accountRepository.findAll().stream()
                .filter(acc -> LocalDate.now().isAfter(acc.getDateOpened()) &&
                        acc.getDateClosed() == null &&
                        isBankNameEquals(cardHolderData.getAccountNumber().substring(1,6), bankName) &&
                        _passwordEncoder.matches(cardHolderData.getAccountNumber(), acc.getAccountNumber()) &&
                        acc.getName().equals(cardHolderData.getCardHolderName()) &&
                        Objects.requireNonNull(_customerAccountRepository.findById(acc.getId()).orElse(null)).getValidThru().equals(cardHolderData.getValidThru()) &&
                        _passwordEncoder.matches(cardHolderData.getSecurityCode(), Objects.requireNonNull(_customerAccountRepository.findById(acc.getId()).orElse(null)).getSecurityCode()))
                .findFirst();
    }

    private boolean isBankNameEquals(String bankCode, String bankName) {
        String bankNameFromBankCode = getBankNameFromBankCode(bankCode);
        return bankNameFromBankCode.equals(bankName);
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

    private PaymentRequest getPaymentRequestFromPaymentCounter(String paymentCounter, String bankName) {
        PaymentRequest paymentRequest = getPaymentRequest(Integer.parseInt(paymentCounter), bankName);
        if(paymentRequest != null &&
            !paymentRequest.isDeleted()) {
            return paymentRequest;
        }
        return null;
    }

    private PaymentRequest getPaymentRequest(int paymentCounter, String bankName) {
        List<PaymentRequest> paymentRequestsForPaymentId = _paymentRequestRepository.findByPaymentCounter(paymentCounter);
        for (PaymentRequest paymentRequest : paymentRequestsForPaymentId) {
            MerchantOrder merchantOrder = paymentRequest.getMerchantOrder();
            String bankNameFromPaymentRequest = getBankNameFromBankCode(merchantOrder.getSellerAccount().getBankCode());
            if(bankNameFromPaymentRequest.equals(bankName)) {
                return paymentRequest;
            }
        }
        return null;
    }

}
