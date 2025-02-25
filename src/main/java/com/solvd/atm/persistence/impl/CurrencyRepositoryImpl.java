package com.solvd.atm.persistence.impl;

import com.solvd.atm.domain.ATMElements.Currency;
import com.solvd.atm.persistence.CurrencyRepository;
import com.solvd.atm.persistence.MybatisSessionHolder;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CurrencyRepositoryImpl implements CurrencyRepository {

    @Override
    public List<Currency> getAll() {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            CurrencyRepository repository = session.getMapper(CurrencyRepository.class);
            return repository.getAll();
        }
    }

    @Override
    public Currency getById(String isoCode) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            CurrencyRepository repository = session.getMapper(CurrencyRepository.class);
            return repository.getById(isoCode);
        }
    }

    @Override
    public void create(Currency currency) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            CurrencyRepository repository = session.getMapper(CurrencyRepository.class);
            repository.create(currency);
        }
    }
}