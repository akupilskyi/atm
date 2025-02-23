package com.solvd.atm.domain.AccountResources;

import com.solvd.atm.domain.ATMElements.Currency;

import java.math.BigDecimal;

public class Account {

    private Integer id;
    private BigDecimal balance;
    private Currency currency;
    private boolean isClosed; // default value is false

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean closed) {
        isClosed = closed;
    }
}