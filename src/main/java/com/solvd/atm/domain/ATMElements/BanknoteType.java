package com.solvd.atm.domain.ATMElements;

public class BanknoteType {

    private Long id;
    private Currency currency;
    private Long denomination;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getDenomination() {
        return denomination;
    }

    public void setDenomination(Long denomination) {
        this.denomination = denomination;
    }
}