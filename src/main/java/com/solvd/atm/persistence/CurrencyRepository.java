package com.solvd.atm.persistence;

import com.solvd.atm.domain.ATMElements.Currency;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CurrencyRepository {

    List<Currency> getAll();

    Currency getById(@Param("isoCode") String isoCode);

    void create(Currency currency);
}