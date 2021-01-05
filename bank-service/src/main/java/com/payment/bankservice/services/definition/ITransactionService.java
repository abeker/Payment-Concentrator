package com.payment.bankservice.services.definition;


import com.payment.bankservice.dto.request.CardHolderData;
import com.payment.bankservice.dto.request.RequestPcc;
import com.payment.bankservice.dto.response.ResponsePcc;
import com.payment.bankservice.dto.response.TransactionResponse;

public interface ITransactionService {

    TransactionResponse pay(CardHolderData cardHolderData, String bankName) throws IllegalAccessException, NoSuchFieldException;

    ResponsePcc payPcc(RequestPcc requestPcc) throws IllegalAccessException;
}
