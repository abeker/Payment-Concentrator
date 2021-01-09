package com.payment.bankservice.services.definition;

import com.payment.bankservice.dto.request.AccountRequest;
import com.payment.bankservice.dto.response.AccountResponse;

public interface IAccountService {

    AccountResponse createSellerAccount(AccountRequest accountRequest);

}
