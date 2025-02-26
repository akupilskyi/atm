package com.solvd.atm.persistence.impl;

import com.solvd.atm.domain.ATMElements.BanknoteType;
import com.solvd.atm.persistence.BanknoteTypeRepository;
import com.solvd.atm.persistence.MybatisSessionHolder;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BanknoteTypeRepositoryImpl implements BanknoteTypeRepository {

    @Override
    public List<BanknoteType> getAll() {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            BanknoteTypeRepository repository = session.getMapper(BanknoteTypeRepository.class);
            return repository.getAll();
        }
    }

    @Override
    public BanknoteType getById(Long id) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            BanknoteTypeRepository repository = session.getMapper(BanknoteTypeRepository.class);
            return repository.getById(id);
        }
    }

    @Override
    public void create(BanknoteType banknoteType) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            BanknoteTypeRepository repository = session.getMapper(BanknoteTypeRepository.class);
            repository.create(banknoteType);
        }
    }
}