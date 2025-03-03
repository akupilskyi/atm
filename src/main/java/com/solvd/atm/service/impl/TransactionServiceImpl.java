package com.solvd.atm.service.impl;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.domain.ATMElements.ExchangeRate;
import com.solvd.atm.domain.AccountResources.Card;
import com.solvd.atm.domain.AccountResources.Transaction;
import com.solvd.atm.persistence.TransactionRepository;
import com.solvd.atm.persistence.impl.TransactionRepositoryImpl;
import com.solvd.atm.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl() {
        this.transactionRepository = new TransactionRepositoryImpl();
    }

    @Override
    public void create(Transaction transaction, Card card, ATM atm, ExchangeRate exchangeRate) {
        transaction.setId(null);
        transactionRepository.create(transaction, card, atm, exchangeRate);
    }
}
