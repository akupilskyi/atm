package com.solvd.atm.persistence.impl;

import com.solvd.atm.domain.AccountResources.Transaction;
import com.solvd.atm.persistence.MybatisSessionHolder;
import com.solvd.atm.persistence.TransactionRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository {

    @Override
    public List<Transaction> getAll() {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            TransactionRepository repository = session.getMapper(TransactionRepository.class);
            return repository.getAll();
        }
    }

    @Override
    public Transaction getById(Long id) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            TransactionRepository repository = session.getMapper(TransactionRepository.class);
            return repository.getById(id);
        }
    }

    @Override
    public void create(Transaction transaction) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            TransactionRepository repository = session.getMapper(TransactionRepository.class);
            repository.create(transaction);
        }
    }
}