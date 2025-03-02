package com.solvd.atm;

import com.solvd.atm.controller.ATMController;
import com.solvd.atm.service.ATMService;
import com.solvd.atm.service.AccountService;
import com.solvd.atm.service.ExchangeRateService;
import com.solvd.atm.service.TransactionService;
import com.solvd.atm.service.impl.ATMServiceImpl;
import com.solvd.atm.service.impl.AccountServiceImpl;
import com.solvd.atm.service.impl.ExchangeRateServiceImpl;
import com.solvd.atm.service.impl.TransactionServiceImpl;
import com.solvd.atm.view.ATMView;

public class Main {

    public static void main(String[] args) {

        ATMService atmService = new ATMServiceImpl();
        AccountService accountService = new AccountServiceImpl();
        ExchangeRateService exchangeRateService = new ExchangeRateServiceImpl();
        TransactionService transactionService = new TransactionServiceImpl();

        ATMView atmView = new ATMView();

        ATMController atmController = new ATMController(atmView, accountService, exchangeRateService, atmService, transactionService);

        atmController.start();
    }
}