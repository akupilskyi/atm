package com.solvd.atm.domain.AccountResources;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.domain.ATMElements.ExchangeRate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

public class Transaction {

    private BigInteger id;
    private Card card;
    private TransactionType transactionType;
    private BigDecimal amount;
    private Timestamp timestamp;
    private ATM atm;
    private ExchangeRate exchangeRate;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
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

    public ATM getATM() {
        return atm;
    }

    public void setATM(ATM atm) {
        this.atm = atm;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}