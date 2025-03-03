package com.solvd.atm.persistence;

import com.solvd.atm.domain.ATMElements.ExchangeRate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExchangeRateRepository {

    List<ExchangeRate> getAll();

    ExchangeRate getById(@Param("id") Long id);

    void create(ExchangeRate exchangeRate);

    ExchangeRate getByFromCurrencyAndToCurrency(@Param("fromCurrency") String fromCurrency, @Param("toCurrency") String toCurrency);
}