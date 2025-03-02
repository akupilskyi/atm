package com.solvd.atm.domain.AccountResources;

import com.solvd.atm.domain.ATMElements.Currency;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private Long id;
    private BigDecimal balance;
    private Currency currency;
    private boolean locked; // default value is false
    private List<Card> cards;
    private final Lock lock = new ReentrantLock();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void lockAccount() {
        lock.lock();
    }

    public void unlockAccount() {
        lock.unlock();
    }
}