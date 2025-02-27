package com.solvd.atm.domain.ATMElements;

public class Currency {

    private String isoCode; // https://en.wikipedia.org/wiki/ISO_4217#List_of_ISO_4217_currency_codes
    private String name;

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}