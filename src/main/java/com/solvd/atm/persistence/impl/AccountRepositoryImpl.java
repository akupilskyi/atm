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
    public Account getById(Integer id) {
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
    public void updateBalance(Integer accountId, BigDecimal newBalance) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            AccountRepository repository = session.getMapper(AccountRepository.class);
            repository.updateBalance(accountId, newBalance);
        }
    }
}