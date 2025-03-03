package com.solvd.atm.service.impl;

import com.solvd.atm.persistence.BanknoteTypeRepository;
import com.solvd.atm.persistence.impl.BanknoteTypeRepositoryImpl;
import com.solvd.atm.service.BanknoteTypeService;

public class BanknoteTypeServiceImpl implements BanknoteTypeService {

    private final BanknoteTypeRepository banknoteTypeRepository;

    public BanknoteTypeServiceImpl() {
        this.banknoteTypeRepository = new BanknoteTypeRepositoryImpl();
    }
}