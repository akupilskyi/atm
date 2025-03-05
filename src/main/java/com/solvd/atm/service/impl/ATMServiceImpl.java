package com.solvd.atm.service.impl;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.domain.ATMElements.IndividualBanknote;
import com.solvd.atm.domain.ATMElements.Status;
import com.solvd.atm.persistence.ATMRepository;
import com.solvd.atm.persistence.impl.ATMRepositoryImpl;
import com.solvd.atm.service.ATMService;

import java.util.*;

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

    @Override
    public ATM selectActiveATM(List<ATM> atms, Long atmId) {
        ATM selectedATM = getById(atmId);
        if (selectedATM != null && selectedATM.getStatus() == Status.active) {
            return selectedATM;
        }
        return null;
    }

    @Override
    public Map<String, Integer> getAvailableCurrencies(ATM atm) {
        Map<String, Integer> currencyCount = new HashMap<>();
        List<IndividualBanknote> banknotes = atm.getBanknotes();
        if (banknotes != null && !banknotes.isEmpty()) {
            for (IndividualBanknote banknote : banknotes) {
                String currencyCode = banknote.getBanknoteType().getCurrency().getIsoCode();
                currencyCount.put(currencyCode, currencyCount.getOrDefault(currencyCode, 0) + 1);
            }
        }
        return currencyCount;
    }

    @Override
    public Set<Long> getAvailableDenominations(ATM atm, String currency) {
        Set<Long> availableDenominations = new TreeSet<>();
        for (IndividualBanknote banknote : atm.getBanknotes()) {
            if (banknote.getBanknoteType().getCurrency().getIsoCode().equals(currency)) {
                availableDenominations.add(banknote.getBanknoteType().getDenomination());
            }
        }
        return availableDenominations;
    }

    @Override
    public List<IndividualBanknote> getBanknotesByCurrency(ATM atm, String currency) {
        List<IndividualBanknote> filteredBanknotes = new ArrayList<>();
        for (IndividualBanknote banknote : atm.getBanknotes()) {
            if (banknote.getBanknoteType().getCurrency().getIsoCode().equals(currency)) {
                filteredBanknotes.add(banknote);
            }
        }
        return filteredBanknotes;
    }
}