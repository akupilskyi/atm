package com.solvd.atm.service.impl;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.domain.ATMElements.ExchangeRate;
import com.solvd.atm.domain.ATMElements.IndividualBanknote;
import com.solvd.atm.domain.AccountResources.Account;
import com.solvd.atm.domain.AccountResources.Card;
import com.solvd.atm.domain.AccountResources.Transaction;
import com.solvd.atm.domain.AccountResources.TransactionType;
import com.solvd.atm.persistence.TransactionRepository;
import com.solvd.atm.persistence.impl.TransactionRepositoryImpl;
import com.solvd.atm.service.ATMService;
import com.solvd.atm.service.AccountService;
import com.solvd.atm.service.ExchangeRateService;
import com.solvd.atm.service.TransactionService;
import com.solvd.atm.view.ATMView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ExchangeRateService exchangeRateService;
    private final ATMService atmService;
    private final AccountService accountService;
    private final ATMView view;

    public TransactionServiceImpl() {
        this.view = new ATMView();
        this.transactionRepository = new TransactionRepositoryImpl();
        this.exchangeRateService = new ExchangeRateServiceImpl();
        this.atmService = new ATMServiceImpl();
        this.accountService = new AccountServiceImpl();
    }

    @Override
    public void create(Transaction transaction, Card card, ATM atm, ExchangeRate exchangeRate) {
        transaction.setId(null);
        transactionRepository.create(transaction, card, atm, exchangeRate);
    }

    @Override
    public boolean processWithdrawal(Account account, Card card, ATM atm, BigDecimal amount, String selectedCurrency) {

        // Step 1: Convert amount to account currency if necessary
        BigDecimal amountInCardCurrency = amount;
        ExchangeRate atmToCardRate = null;

        if (!selectedCurrency.equals(card.getCurrency().getIsoCode())) {
            atmToCardRate = exchangeRateService.getByFromCurrencyAndToCurrency(
                    selectedCurrency, card.getCurrency().getIsoCode());
            if (atmToCardRate == null) {
                System.out.println("Failed: Exchange rate not available for " + selectedCurrency + " to " + card.getCurrency().getIsoCode());
                return false;
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
                System.out.println("Failed: Exchange rate not available for " + card.getCurrency().getIsoCode() + " to " + account.getCurrency().getIsoCode());
                return false;
            }
            amountInAccountCurrency = amountInCardCurrency.multiply(cardToAccountRate.getRate())
                    .setScale(2, RoundingMode.HALF_UP);
        }

        // Step 2: Check if the account has sufficient funds
        if (amountInAccountCurrency.compareTo(account.getBalance()) > 0) {
            System.out.println("Failed: Insufficient funds. Account balance=" + account.getBalance() +
                    ", Required=" + amountInAccountCurrency);
            return false;
        }

        // Step 3: Dispense banknotes
        System.out.println("Retrieving banknotes for currency: " + selectedCurrency);
        List<IndividualBanknote> filteredBanknotes = atmService.getBanknotesByCurrency(atm, selectedCurrency);
        if (filteredBanknotes.isEmpty()) {
            System.out.println("Failed: No banknotes available in the selected currency=" + selectedCurrency);
            return false;
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
            System.out.println("Failed: Cannot dispense the exact amount with the available denominations.");
            return false;
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
            return false;
        }

        // Step 4: Update banknotes in the ATM after dispensing
        List<String> chosenCombination = validCombinations.get(userChoice - 1);
        for (String combination : chosenCombination) {
            String[] parts = combination.split(" x ");
            int quantityToDispense = Integer.parseInt(parts[0]);
            long denomination = Long.parseLong(parts[1]);

            Long banknoteTypeId = null;
            for (IndividualBanknote banknote : filteredBanknotes) {
                if (banknote.getBanknoteType().getDenomination() == denomination) {
                    banknoteTypeId = banknote.getBanknoteType().getId();
                    break;
                }
            }

            if (banknoteTypeId != null) {
                int currentQuantity = denominationCounts.getOrDefault(denomination, 0);
                int newQuantity = currentQuantity - quantityToDispense;

                if (newQuantity < 0) {
                    System.out.println("Failed: Insufficient banknotes in ATM for denomination=" + denomination);
                    return false;
                }
                atmService.updateBanknotes(atm.getId(), banknoteTypeId, newQuantity);
            }
        }

        // Step 5: Update account balance and record the transaction
        account.lockAccount(); // Acquire lock
        try {
            // Re-fetch the latest account state
            Account lockedAccount = accountService.getAccountByCardNumber(card.getNumber());
            if (lockedAccount == null) {
                System.out.println("Failed: Account not found.");
                return false;
            }

            // Re-check balance with the latest data
            BigDecimal currentBalance = lockedAccount.getBalance();
            if (amountInAccountCurrency.compareTo(currentBalance) > 0) {
                System.out.println("Failed: Insufficient funds after re-check. Account balance=" + currentBalance +
                        ", Required=" + amountInAccountCurrency);
                return false;
            }

            // Update the balance locally and then save it to the database
            lockedAccount.setBalance(currentBalance.subtract(amountInAccountCurrency));
            accountService.updateBalance(lockedAccount.getId(), lockedAccount.getBalance());

            // Record transaction
            Transaction transaction = new Transaction();
            transaction.setAmount(amountInAccountCurrency);
            transaction.setTransactionType(TransactionType.withdrawal);
            transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
            create(transaction, card, atm, atmToCardRate);

            return true;
        } finally {
            account.unlockAccount(); // Release lock
        }
    }
}