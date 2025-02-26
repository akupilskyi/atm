package com.solvd.atm.domain.AccountResources;

import com.solvd.atm.domain.ATMElements.ExchangeRate;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {

    private Long id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private Timestamp timestamp;
    private ExchangeRate exchangeRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}