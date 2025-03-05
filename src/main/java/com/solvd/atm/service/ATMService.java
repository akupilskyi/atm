package com.solvd.atm.service;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.domain.ATMElements.IndividualBanknote;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ATMService {

    List<ATM> getAll();

    ATM getById(Long id);

    void updateBanknotes(Long atmId, Long banknoteTypeId, Integer quantity);

    ATM selectActiveATM(List<ATM> atms, Long atmId);

    Map<String, Integer> getAvailableCurrencies(ATM atm);

    Set<Long> getAvailableDenominations(ATM atm, String currency);

    List<IndividualBanknote> getBanknotesByCurrency(ATM atm, String currency);
}