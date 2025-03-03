package com.solvd.atm.service.impl;

import com.solvd.atm.domain.ATMElements.ExchangeRate;
import com.solvd.atm.persistence.ExchangeRateRepository;
import com.solvd.atm.persistence.impl.ExchangeRateRepositoryImpl;
import com.solvd.atm.service.ExchangeRateService;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl() {
        this.exchangeRateRepository = new ExchangeRateRepositoryImpl();
    }

    @Override
    public ExchangeRate getByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency) {
        return exchangeRateRepository.getByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
    }
}
