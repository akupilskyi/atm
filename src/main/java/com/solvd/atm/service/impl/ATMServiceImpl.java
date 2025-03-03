package com.solvd.atm.service.impl;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.persistence.ATMRepository;
import com.solvd.atm.persistence.impl.ATMRepositoryImpl;
import com.solvd.atm.service.ATMService;

import java.util.List;

public class ATMServiceImpl implements ATMService {

    private final ATMRepository atmRepository;


    public ATMServiceImpl() {
        this.atmRepository = new ATMRepositoryImpl();
    }

    @Override
    public List<ATM> getAll() {
        return atmRepository.getAll();
    }

    @Override
    public ATM getById(Long id) {
        return atmRepository.getById(id);
    }

    @Override
    public void updateBanknotes(Long atmId, Long banknoteTypeId, Integer quantity) {
        atmRepository.updateBanknotes(atmId, banknoteTypeId, quantity);
    }
}