package com.solvd.atm.service;

import com.solvd.atm.domain.ATMElements.ATM;

import java.util.List;

public interface ATMService {

    List<ATM> getAll();

    ATM getById(Long id);

    void updateBanknotes(Long atmId, Long banknoteTypeId, Integer quantity);
}