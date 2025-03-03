package com.solvd.atm.controller;

import com.solvd.atm.domain.ATMElements.Currency;
import com.solvd.atm.domain.ATMElements.*;
import com.solvd.atm.domain.AccountResources.Account;
import com.solvd.atm.domain.AccountResources.Card;
import com.solvd.atm.domain.AccountResources.Transaction;
import com.solvd.atm.domain.AccountResources.TransactionType;
import com.solvd.atm.service.ATMService;
import com.solvd.atm.service.AccountService;
import com.solvd.atm.service.ExchangeRateService;
import com.solvd.atm.service.TransactionService;
import com.solvd.atm.view.ATMView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ATMController {

    private final ATMView view;
    private final AccountService accountService;
    private final ExchangeRateService exchangeRateService;
    private final ATMService atmService;
    private final TransactionService transactionService;

    public ATMController(ATMView view, AccountService accountService, ExchangeRateService exchangeRateService, ATMService atmService, TransactionService transactionService) {
        this.view = view;
        this.accountService = accountService;
        this.exchangeRateService = exchangeRateService;
        this.atmService = atmService;
        this.transactionService = transactionService;
    }

    public void start() {
        // Step 1: Select ATM
        List<ATM> atms = atmService.getAll();
        ATM selectedATM = null;

        while (selectedATM == null || selectedATM.getStatus() != Status.active) {
            Long atmId = view.selectATM(atms);
            selectedATM = atmService.getById(atmId);
            if (selectedATM.getStatus() != Status.active) {
                view.displayMessage("This ATM is out of service. Please select another ATM.");
            }
        }

        // Step 2: Authenticate user with card number and PIN
        String cardNumber = view.askForCardNumber();
        Account account = accountService.getAccountByCardNumber(cardNumber);
        if (account == null) {
            view.displayMessage("No account found for this card number.");
            return;
        }

        String pin = view.askForPin();
        Card card = null;
        for (Card c : account.getCards()) {
            if (c.getNumber().equals(cardNumber) && c.getPin().equals(pin)) {
                card = c;
                break;
            }
        }
        if (card == null) {
            view.displayMessage("Invalid PIN.");
            return;
        }

        // Step 3: Display account balance
        view.displayMessage("Your balance: " + account.getBalance() + " " + account.getCurrency().getIsoCode());

        // Retrieve currency info of ATM
        Map<String, Integer> currencyCount = new HashMap<>();
        Map<Long, Integer> banknoteQuantities = new HashMap<>();
        List<IndividualBanknote> banknotes = selectedATM.getBanknotes();
        if (banknotes != null && !banknotes.isEmpty()) {
            for (IndividualBanknote banknote : banknotes) {
                BanknoteType banknoteType = banknote.getBanknoteType();
                if (banknoteType != null) {
                    Currency currency = banknoteType.getCurrency();
                    if (currency != null) {
                        String currencyCode = currency.getIsoCode();
                        currencyCount.put(currencyCode, currencyCount.getOrDefault(currencyCode, 0) + 1);
                        Long banknoteTypeId = banknoteType.getId();
                        banknoteQuantities.put(banknoteTypeId, banknoteQuantities.getOrDefault(banknoteTypeId, 0) + 1);
                    }
                }
            }
            view.displayAvailableCurrencies(currencyCount);
        } else {
            view.displayMessage("No banknotes found in this ATM.");
            return;
        }

        // Step 4: Select a currency for withdrawal
        String selectedCurrency = null;
        while (selectedCurrency == null || !currencyCount.containsKey(selectedCurrency)) {
            selectedCurrency = view.askForCurrency(currencyCount.keySet());
            if (!currencyCount.containsKey(selectedCurrency)) {
                view.displayMessage("Invalid currency selection. Please try again.");
            }
        }

        //Display available denominations for the selected currency
        Set<Long> availableDenominations = new TreeSet<>();
        for (IndividualBanknote banknote : selectedATM.getBanknotes()) {
            if (banknote.getBanknoteType().getCurrency().getIsoCode().equals(selectedCurrency)) {
                availableDenominations.add(banknote.getBanknoteType().getDenomination());
            }
        }

        if (!availableDenominations.isEmpty()) {
            view.displayMessage("Available denominations: " + availableDenominations);
        } else {
            view.displayMessage("No available denominations for the selected currency.");
            return;
        }

        // Step 5: Ask for withdrawal amount
        BigDecimal amount = view.askForAmount();

        // Step 6: Convert amount to account currency if necessary
        BigDecimal amountInCardCurrency = amount;
        ExchangeRate atmToCardRate = null;

        if (!selectedCurrency.equals(card.getCurrency().getIsoCode())) {
            atmToCardRate = exchangeRateService.getByFromCurrencyAndToCurrency(
                    selectedCurrency, card.getCurrency().getIsoCode());
            if (atmToCardRate == null) {
                view.displayMessage("Exchange rate not available for the selected currencies.");
                return;
            }
            amountInCardCurrency = amount.multiply(atmToCardRate.getRate())
                                         .setScale(2, RoundingMode.HALF_UP);
        } else {
            atmToCardRate = new ExchangeRate();
            atmToCardRate.setRate(BigDecimal.ONE);
        }

        // Convert the amount from card currency to account currency
        BigDecimal amountInAccountCurrency = amountInCardCurrency;
        if (!card.getCurrency().getIsoCode().equals(account.getCurrency().getIsoCode())) {
            ExchangeRate cardToAccountRate = exchangeRateService.getByFromCurrencyAndToCurrency(
                    card.getCurrency().getIsoCode(), account.getCurrency().getIsoCode());
            if (cardToAccountRate == null) {
                view.displayMessage("Exchange rate not available for the selected currencies.");
                return;
            }
            amountInAccountCurrency = amountInCardCurrency.multiply(cardToAccountRate.getRate())
                                                          .setScale(2, RoundingMode.HALF_UP);
        }

        // Check if the account has sufficient funds
        if (amountInAccountCurrency.compareTo(account.getBalance()) > 0) {
            view.displayMessage("Insufficient funds.");
            return;
        }

        // Step 7: Dispense banknotes
        List<IndividualBanknote> filteredBanknotes = new ArrayList<>();
        for (IndividualBanknote banknote : selectedATM.getBanknotes()) {
            if (banknote.getBanknoteType().getCurrency().getIsoCode().equals(selectedCurrency)) {
                filteredBanknotes.add(banknote);
            }
        }

        if (filteredBanknotes.isEmpty()) {
            view.displayMessage("No banknotes available in the selected currency.");
            return;
        }

        // Calculate available denominations and their counts
        Map<Long, Integer> denominationCounts = new HashMap<>();
        for (IndividualBanknote banknote : filteredBanknotes) {
            Long denomination = banknote.getBanknoteType().getDenomination();
            denominationCounts.put(denomination, denominationCounts.getOrDefault(denomination, 0) + 1);
        }

        // Find valid combinations of denominations to dispense the requested amount
        List<List<String>> validCombinations = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : denominationCounts.entrySet()) {
            Long denomination = entry.getKey();
            int availableCount = entry.getValue();

            int billsNeeded = amount.divide(BigDecimal.valueOf(denomination), RoundingMode.FLOOR).intValue();
            int billsToDispense = Math.min(billsNeeded, availableCount);

            if (billsToDispense > 0) {
                BigDecimal total = BigDecimal.valueOf(denomination).multiply(BigDecimal.valueOf(billsToDispense));
                if (total.compareTo(amount) == 0) {
                    validCombinations.add(Collections.singletonList(billsToDispense + " x " + denomination));
                }
            }
        }

        if (validCombinations.isEmpty()) {
            view.displayMessage("Sorry, the ATM cannot dispense the exact amount with the available denominations. Please try again.");
            return;
        }

        // Display valid denomination combinations to the user
        StringBuilder optionsMessage = new StringBuilder("Choose a denomination combination for your withdrawal:\n");
        for (int i = 0; i < validCombinations.size(); i++) {
            optionsMessage.append("Option ").append(i + 1).append(": ").append(String.join(", ", validCombinations.get(i))).append("\n");
        }
        view.displayMessage(optionsMessage.toString());

        // Let the user choose a denomination combination
        int userChoice = view.askForOptionChoice(validCombinations.size());
        if (userChoice < 1 || userChoice > validCombinations.size()) {
            view.displayMessage("Invalid choice. Please select a valid option.");
            return;
        }

        // Step 8: Update banknotes in the ATM after dispensing
        List<String> chosenCombination = validCombinations.get(userChoice - 1);
        for (String combination : chosenCombination) {
            String[] parts = combination.split(" x ");
            int quantityToDispense = Integer.parseInt(parts[0]);
            long denomination = Long.parseLong(parts[1]);

            Long banknoteTypeId = null;
            for (IndividualBanknote banknote : banknotes) {
                if (banknote.getBanknoteType().getDenomination() == denomination) {
                    banknoteTypeId = banknote.getBanknoteType().getId();
                    break;
                }
            }

            if (banknoteTypeId != null) {
                int currentQuantity = banknoteQuantities.getOrDefault(banknoteTypeId, 0);
                int newQuantity = currentQuantity - quantityToDispense;

                if (newQuantity < 0) {
                    view.displayMessage("Insufficient banknotes in ATM for denomination: " + denomination);
                    return;
                }
                banknoteQuantities.put(banknoteTypeId, newQuantity);
                atmService.updateBanknotes(selectedATM.getId(), banknoteTypeId, newQuantity);
            }
        }

        // Step 9: Update account balance and record the transaction
        account.lockAccount(); // Acquire lock
        try {
            // Re-fetch the latest account state
            Account lockedAccount = accountService.getAccountByCardNumber(cardNumber);
            if (lockedAccount == null) {
                view.displayMessage("Account not found.");
                return;
            }

            // Re-check balance with the latest data
            BigDecimal currentBalance = lockedAccount.getBalance();
            if (amountInAccountCurrency.compareTo(currentBalance) > 0) {
                view.displayMessage("Insufficient funds.");
                return;
            }

            // Update the balance locally and then save it to the database
            lockedAccount.setBalance(currentBalance.subtract(amountInAccountCurrency));
            accountService.updateBalance(lockedAccount.getId(), lockedAccount.getBalance());

            // Record transaction
            Transaction transaction = new Transaction();
            transaction.setAmount(amountInAccountCurrency);
            transaction.setTransactionType(TransactionType.withdrawal);
            transactionService.create(transaction, card, selectedATM, atmToCardRate);

            view.displayMessage("Withdrawal successful. Please take your cash.");
            view.displayMessage("Your new balance: " + lockedAccount.getBalance() + " " + lockedAccount.getCurrency().getIsoCode());
        } finally {
            account.unlockAccount(); // Release lock
        }
    }
}