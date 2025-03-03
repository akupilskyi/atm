package com.solvd.atm.service;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.domain.ATMElements.ExchangeRate;
import com.solvd.atm.domain.AccountResources.Card;
import com.solvd.atm.domain.AccountResources.Transaction;

public interface TransactionService {

    void create(Transaction transaction, Card card, ATM atm, ExchangeRate exchangeRate);
}