package com.payment.raiffeisenservice.services.definition;


import com.payment.raiffeisenservice.dto.request.CardHolderData;
import com.payment.raiffeisenservice.dto.response.TransactionResponse;

public interface ITransactionService {

    TransactionResponse pay(CardHolderData cardHolderData) throws IllegalAccessException, NoSuchFieldException;

}
