package com.payment.unicreditservice.services.definition;

import com.payment.unicreditservice.dto.request.CardHolderData;
import com.payment.unicreditservice.dto.request.RequestPcc;
import com.payment.unicreditservice.dto.response.ResponsePcc;
import com.payment.unicreditservice.dto.response.TransactionResponse;

public interface ITransactionService {

    TransactionResponse pay(CardHolderData cardHolderData) throws IllegalAccessException, NoSuchFieldException;

    ResponsePcc payPcc(RequestPcc requestPcc) throws IllegalAccessException;
}
