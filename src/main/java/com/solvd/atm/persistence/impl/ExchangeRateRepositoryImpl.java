package com.solvd.atm.persistence.impl;

import com.solvd.atm.domain.ATMElements.ExchangeRate;
import com.solvd.atm.persistence.ExchangeRateRepository;
import com.solvd.atm.persistence.MybatisSessionHolder;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ExchangeRateRepositoryImpl implements ExchangeRateRepository {

    @Override
    public List<ExchangeRate> getAll() {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            ExchangeRateRepository repository = session.getMapper(ExchangeRateRepository.class);
            return repository.getAll();
        }
    }

    @Override
    public ExchangeRate getById(Long id) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            ExchangeRateRepository repository = session.getMapper(ExchangeRateRepository.class);
            return repository.getById(id);
        }
    }

    @Override
    public void create(ExchangeRate exchangeRate) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            ExchangeRateRepository repository = session.getMapper(ExchangeRateRepository.class);
            repository.create(exchangeRate);
        }
    }

    @Override
    public ExchangeRate getByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            ExchangeRateRepository repository = session.getMapper(ExchangeRateRepository.class);
            return repository.getByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        }
    }
}