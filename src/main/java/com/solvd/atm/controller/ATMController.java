package com.solvd.atm.controller;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.domain.AccountResources.Account;
import com.solvd.atm.domain.AccountResources.Card;
import com.solvd.atm.service.ATMService;
import com.solvd.atm.service.AccountService;
import com.solvd.atm.service.TransactionService;
import com.solvd.atm.view.ATMView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ATMController {

    private final ATMView view;
    private final AccountService accountService;
    private final ATMService atmService;
    private final TransactionService transactionService;

    public ATMController(ATMView view, AccountService accountService, ATMService atmService, TransactionService transactionService) {
        this.view = view;
        this.accountService = accountService;
        this.atmService = atmService;
        this.transactionService = transactionService;
    }

    public void start() {
        // Step 1: Select ATM
        ATM selectedATM = null;
        while (selectedATM == null) {
            List<ATM> atms = atmService.getAll();
            Long atmId = view.selectATM(atms);
            selectedATM = atmService.selectActiveATM(atms, atmId);

            if (selectedATM == null) {
                view.displayMessage("This ATM is out of service. Please select another ATM.");
            }
        }

        // Step 2: Authenticate user with card number and PIN
        Card card = null;
        while (card == null) {
            String cardNumber = view.askForCardNumber();
            String pin = view.askForPin();
            card = accountService.authenticateUser(cardNumber, pin);

            if (card == null) {
                view.displayMessage("Invalid card number or PIN. Please try again.");
            }
        }

        Account account = accountService.getAccountByCardNumber(card.getNumber());
        view.displayMessage("Your balance: " + account.getBalance() + " " + account.getCurrency().getIsoCode());

        // Step 3: Display available currencies
        Map<String, Integer> currencyCount = atmService.getAvailableCurrencies(selectedATM);
        view.displayAvailableCurrencies(currencyCount);

        // Step 4: Select a currency for withdrawal
        String selectedCurrency = null;
        while (selectedCurrency == null || !currencyCount.containsKey(selectedCurrency)) {
            selectedCurrency = view.askForCurrency(currencyCount.keySet());
            if (!currencyCount.containsKey(selectedCurrency)) {
                view.displayMessage("Invalid currency selection. Please try again.");
            }
        }

        // Step 5: Display available denominations
        Set<Long> availableDenominations = atmService.getAvailableDenominations(selectedATM, selectedCurrency);
        view.displayMessage("Available denominations: " + availableDenominations);

        // Step 6: Ask for withdrawal amount
        BigDecimal amount = null;
        while (amount == null) {
            try {
                amount = view.askForAmount();
            } catch (NumberFormatException e) {
                view.displayMessage("Invalid amount. Please enter a valid number.");
            }
        }

        // Step 7: Process withdrawal
        boolean withdrawalSuccess = transactionService.processWithdrawal(account, card, selectedATM, amount, selectedCurrency);
        Account updatedAccount = accountService.getAccountByCardNumber(card.getNumber());

        if (withdrawalSuccess) {
            view.displayMessage("Withdrawal successful. Please take your cash.");
            view.displayMessage("Your new balance: " + updatedAccount.getBalance() + " " + updatedAccount.getCurrency().getIsoCode());
        } else {
            view.displayMessage("Withdrawal failed. Please try again.");
        }
    }
}