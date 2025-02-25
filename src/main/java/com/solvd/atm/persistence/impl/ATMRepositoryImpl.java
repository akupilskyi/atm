package com.solvd.atm.persistence.impl;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.persistence.ATMRepository;
import com.solvd.atm.persistence.MybatisSessionHolder;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ATMRepositoryImpl implements ATMRepository {

    @Override
    public List<ATM> getAll() {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            ATMRepository repository = session.getMapper(ATMRepository.class);
            return repository.getAll();
        }
    }

    @Override
    public ATM getById(Integer id) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            ATMRepository repository = session.getMapper(ATMRepository.class);
            return repository.getById(id);
        }
    }

    @Override
    public void create(ATM atm) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            ATMRepository repository = session.getMapper(ATMRepository.class);
            repository.create(atm);
        }
    }
}