package com.solvd.atm.service;

import com.solvd.atm.domain.ATMElements.ExchangeRate;

public interface ExchangeRateService {

    ExchangeRate getByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);

}
