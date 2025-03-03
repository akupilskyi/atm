package com.solvd.atm.view;

import com.solvd.atm.domain.ATMElements.ATM;

import java.math.BigDecimal;
import java.util.*;

public class ATMView {
    private Scanner scanner = new Scanner(System.in);

    public String askForCardNumber() {
        System.out.print("Enter your card number: ");
        return scanner.nextLine();
    }

    public String askForPin() {
        System.out.print("Enter your PIN: ");
        return scanner.nextLine();
    }

    public BigDecimal askForAmount() {
        System.out.print("Enter the amount to withdraw: ");
        return new BigDecimal(scanner.nextLine());
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public Long selectATM(List<ATM> atms) {
        System.out.println("Select an ATM:");
        for (ATM atm : atms) {
            System.out.println(atm.getId() + ": " + atm.getLocation() + " (" + atm.getStatus() + ")");
        }
        System.out.print("Enter ATM ID: ");
        Long atmId = scanner.nextLong();
        scanner.nextLine();
        return atmId;
    }

    public void displayAvailableCurrencies(Map<String, Integer> currencyCount) {
        System.out.println("Available currencies in this ATM:");
        for (Map.Entry<String, Integer> entry : currencyCount.entrySet()) {
            System.out.println("Currency: " + entry.getKey() + ", Count: " + entry.getValue());
        }
    }

    public String askForCurrency(Set<String> availableCurrencies) {
        System.out.println("Select a currency to withdraw:");
        for (String currency : availableCurrencies) {
            System.out.println(currency);
        }
        System.out.print("Enter currency code: ");
        return scanner.nextLine();
    }

    public int askForOptionChoice(int numberOfOptions) {
        while (true) {
            System.out.print("Please select an option (1 to " + numberOfOptions + "): ");
            try {
                int userChoice = scanner.nextInt();
                scanner.nextLine();
                if (userChoice >= 1 && userChoice <= numberOfOptions) {
                    return userChoice;
                } else {
                    System.out.print("Invalid option. Please select a number between 1 and " + numberOfOptions + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}