package com.solvd.atm.domain.ATMElements;

public class Currency {

    private String code; // https://en.wikipedia.org/wiki/ISO_4217#List_of_ISO_4217_currency_codes

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}