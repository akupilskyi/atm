package com.solvd.atm.service.impl;

import com.solvd.atm.persistence.CurrencyRepository;
import com.solvd.atm.persistence.impl.CurrencyRepositoryImpl;
import com.solvd.atm.service.CurrencyService;

public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl() {
        this.currencyRepository = new CurrencyRepositoryImpl();
    }
}