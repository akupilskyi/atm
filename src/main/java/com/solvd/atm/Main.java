package com.solvd.atm;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.persistence.ATMRepository;
import com.solvd.atm.persistence.impl.ATMRepositoryImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ATMRepository atmRepository = new ATMRepositoryImpl();

        atmRepository.updateBanknotes(1L, 1L, 111);
        atmRepository.updateBanknotes(1L, 2L, 222);
        atmRepository.updateBanknotes(1L, 11L, 333);
        atmRepository.updateBanknotes(2L, 3L, 444);
        atmRepository.updateBanknotes(2L, 4L, 555);
        atmRepository.updateBanknotes(2L, 12L, 666);
        atmRepository.updateBanknotes(3L, 5L, 777);
        atmRepository.updateBanknotes(3L, 6L, 888);

        List<ATM> atms = atmRepository.getAll();

        System.out.println();
    }
}