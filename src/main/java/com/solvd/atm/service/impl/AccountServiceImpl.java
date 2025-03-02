package com.solvd.atm.service.impl;

import com.solvd.atm.domain.AccountResources.Account;
import com.solvd.atm.persistence.AccountRepository;
import com.solvd.atm.persistence.impl.AccountRepositoryImpl;
import com.solvd.atm.service.AccountService;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl() {
        this.accountRepository = new AccountRepositoryImpl();
    }

    @Override
    public void updateBalance(Long accountId, BigDecimal newBalance) {
        accountRepository.updateBalance(accountId, newBalance);
    }

    @Override
    public Account getAccountByCardNumber(String cardNumber) {
        return accountRepository.getAccountByCardNumber(cardNumber);
    }
}