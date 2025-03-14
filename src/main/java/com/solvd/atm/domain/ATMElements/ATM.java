package com.solvd.atm.domain.ATMElements;

import com.solvd.atm.domain.AccountResources.Transaction;

import java.util.List;

public class ATM {

    private Long id;
    private String location;
    private Status status;
    private List<IndividualBanknote> banknotes;
    private List<Transaction> transactions;

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

    public List<IndividualBanknote> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(List<IndividualBanknote> banknotes) {
        this.banknotes = banknotes;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}