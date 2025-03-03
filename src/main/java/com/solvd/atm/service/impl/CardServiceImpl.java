package com.solvd.atm.service.impl;

import com.solvd.atm.persistence.CardRepository;
import com.solvd.atm.persistence.impl.CardRepositoryImpl;
import com.solvd.atm.service.CardService;

public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    public CardServiceImpl() {
        this.cardRepository = new CardRepositoryImpl();
    }
}