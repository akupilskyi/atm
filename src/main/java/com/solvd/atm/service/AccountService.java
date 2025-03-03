package com.solvd.atm.service;

import com.solvd.atm.domain.AccountResources.Account;

import java.math.BigDecimal;

public interface AccountService {

    void updateBalance(Long accountId, BigDecimal newBalance);

    Account getAccountByCardNumber (String cardNumber);
}