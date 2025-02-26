package com.solvd.atm.domain.ATMElements;

import com.solvd.atm.domain.AccountResources.Transaction;

import java.util.List;
import java.util.Map;

public class ATM {

    private Long id;
    private String location;
    private Status status;
    private Map<BanknoteType, Long> banknotes;
    List<Transaction> transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<BanknoteType, Long> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(Map<BanknoteType, Long> banknotes) {
        this.banknotes = banknotes;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}