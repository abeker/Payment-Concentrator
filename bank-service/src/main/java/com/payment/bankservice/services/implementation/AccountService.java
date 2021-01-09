package com.payment.bankservice.services.implementation;

import com.payment.bankservice.dto.request.AccountRequest;
import com.payment.bankservice.dto.response.AccountResponse;
import com.payment.bankservice.repository.IAccountRepository;
import com.payment.bankservice.repository.ISellerAccountRepository;
import com.payment.bankservice.services.definition.IAccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {

    private final IAccountRepository _accountRepository;
    private final ISellerAccountRepository _sellerAccountRepository;

    public AccountService(IAccountRepository accountRepository, ISellerAccountRepository sellerAccountRepository) {
        _accountRepository = accountRepository;
        _sellerAccountRepository = sellerAccountRepository;
    }

    @Override
    public AccountResponse createSellerAccount(AccountRequest accountRequest) {

        return null;
    }
}
