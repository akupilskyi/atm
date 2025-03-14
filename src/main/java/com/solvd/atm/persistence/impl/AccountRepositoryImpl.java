package com.solvd.atm.persistence.impl;

import com.solvd.atm.domain.AccountResources.Account;
import com.solvd.atm.persistence.AccountRepository;
import com.solvd.atm.persistence.MybatisSessionHolder;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public List<Account> getAll() {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            AccountRepository repository = session.getMapper(AccountRepository.class);
            return repository.getAll();
        }
    }

    @Override
    public Account getById(Long id) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            AccountRepository repository = session.getMapper(AccountRepository.class);
            return repository.getById(id);
        }
    }

    @Override
    public void create(Account account) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            AccountRepository repository = session.getMapper(AccountRepository.class);
            repository.create(account);
        }
    }

    @Override
    public void updateBalance(Long accountId, BigDecimal newBalance) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            AccountRepository repository = session.getMapper(AccountRepository.class);
            repository.updateBalance(accountId, newBalance);
        }
    }

    @Override
    public void updateLocked(Long accountId, boolean booleanValue) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            AccountRepository repository = session.getMapper(AccountRepository.class);
            repository.updateLocked(accountId, booleanValue);
        }
    }

    @Override
    public Account getAccountByCardNumber(String cardNumber) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            AccountRepository repository = session.getMapper(AccountRepository.class);
            return repository.getAccountByCardNumber(cardNumber);
        }
    }
}