package com.solvd.atm.domain.ATMElements;

import java.util.Map;

public class ATM {

    private Integer id;
    private String location;
    private Status status;
    private Map<BanknoteType, Integer> banknotes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Map<BanknoteType, Integer> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(Map<BanknoteType, Integer> banknotes) {
        this.banknotes = banknotes;
    }
}