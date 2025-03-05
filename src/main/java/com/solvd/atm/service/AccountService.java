package com.solvd.atm.service;

import com.solvd.atm.domain.AccountResources.Account;
import com.solvd.atm.domain.AccountResources.Card;

import java.math.BigDecimal;

public interface AccountService {

    void updateBalance(Long accountId, BigDecimal newBalance);

    Account getAccountByCardNumber(String cardNumber);

    Card authenticateUser(String cardNumber, String pin);
}