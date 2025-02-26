package com.solvd.atm.persistence.impl;

import com.solvd.atm.domain.AccountResources.Card;
import com.solvd.atm.persistence.CardRepository;
import com.solvd.atm.persistence.MybatisSessionHolder;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    @Override
    public List<Card> getAll() {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            CardRepository repository = session.getMapper(CardRepository.class);
            return repository.getAll();
        }
    }

    @Override
    public Card getById(Long id) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            CardRepository repository = session.getMapper(CardRepository.class);
            return repository.getById(id);
        }
    }

    @Override
    public void create(Card card) {
        try (SqlSession session = MybatisSessionHolder.getSqlSessionFactory().openSession(true)) {
            CardRepository repository = session.getMapper(CardRepository.class);
            repository.create(card);
        }
    }
}