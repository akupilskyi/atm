package com.solvd.atm.persistence;

import com.solvd.atm.domain.ATMElements.ExchangeRate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExchangeRateRepository {

    List<ExchangeRate> getAll();

    ExchangeRate getById(@Param("id") Integer id);

    void create(ExchangeRate exchangeRate);
}