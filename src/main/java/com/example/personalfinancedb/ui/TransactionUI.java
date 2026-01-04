package com.example.personalfinancedb.ui;

import com.example.personalfinancedb.model.Transaction;
import com.example.personalfinancedb.model.TransactionType;
import com.example.personalfinancedb.service.TransactionManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
public class TransactionUI {
    private final Scanner scanner;

    public TransactionUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayAllTransactions() {
        if (TransactionManager.isEmpty()) {
            System.out.println("\nNo transactions yet");
            return;
        }

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ All transactions ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (Transaction t : TransactionManager.getAllTransactions()) {
            System.out.println(t);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private void displayTransactionsWithSummary(List<Transaction> transactions, String period) {
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions was found for " + period);
            return;
        }

        System.out.println("\n~~~~~~~~~~~ Transactions for " + period + " ~~~~~~~~~~~");
        for (Transaction t : transactions) {
            System.out.println(t);
        }

        System.out.println("\n~~~~~~~~~~~ Summary ~~~~~~~~~~~");
        TransactionManager.TransactionSummary summary = TransactionManager.calculateSummary(transactions);
        System.out.println(summary);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public void viewByDay() {
        System.out.print("\nEnter a date (format: yyyy-MM-dd): ");
        String dateString = scanner.nextLine().trim();

        try {
            LocalDate date = LocalDate.parse(dateString);
            List<Transaction> filtered = TransactionManager.getTransactionsByDate(date);
            displayTransactionsWithSummary(filtered, "day: " + dateString);
        } catch (DateTimeParseException e) {
            System.out.println("Incorrect format! Please use the following format: yyyy-MM-dd");
        }
    }

    public void viewByWeek() {
        System.out.print("\nEnter a year and a week number (format: yyyy-VV, ex. 2024-15): ");
        String weekInput = scanner.nextLine().trim();

        try {
            String[] split = weekInput.split("-");
            if (split.length != 2) {
                throw new IllegalArgumentException("Incorrect format!");
            }

            int year = Integer.parseInt(split[0]);
            int week = Integer.parseInt(split[1]);

            if (week < 1 || week > 53) {
                System.out.println("The weeknumber must be between 1 and 53");
                return;
            }

            LocalDate startDate = LocalDate.ofYearDay(year, 1)
                    .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                    .with(DayOfWeek.MONDAY);

            LocalDate endDate = startDate.plusDays(7);

            List<Transaction> filtered = TransactionManager.getTransactionsByWeek(startDate, endDate);
            String period = "Week " + week + ", " + year + " (" + startDate + " - " + endDate.minusDays(1) + ")";
            displayTransactionsWithSummary(filtered, period);
        } catch (Exception e) {
            System.out.println("Incorrect format! Please use the following format: yyyy-VV (ex. 2025-15)");
        }
    }

    public void viewByMonth() {
        System.out.print("\nEnter a year and month (format: yyyy-MM): ");
        String monthInput = scanner.nextLine().trim();

        try {
            YearMonth yearMonth = YearMonth.parse(monthInput);
            List<Transaction> filtered = TransactionManager.getTransactionsByMonth(
                    yearMonth.getYear(), yearMonth.getMonthValue());
            displayTransactionsWithSummary(filtered, "month: " + monthInput);
        } catch (DateTimeParseException e) {
            System.out.println("Incorrect format! Please use the following format: yyyy-MM (ex. 2025-01)");
        }
    }

    public void viewByYear() {
        System.out.print("\nEnter year (format: yyyy): ");
        String yearInput = scanner.nextLine().trim();

        try {
            int year = Integer.parseInt(yearInput);
            if (year < 1900 || year > LocalDate.now().getYear()) {
                System.out.println("The year must be between 1900 and " + LocalDate.now().getYear());
                return;
            }

            List<Transaction> filtered = TransactionManager.getTransactionsByYear(year);
            displayTransactionsWithSummary(filtered, "year: " + yearInput);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format! Please use the following format: yyyy");
        }
    }

    public void viewByType() {
        System.out.println("\nSelect type of transaction:");
        System.out.println("1. Income");
        System.out.println("2. Expense");
        System.out.print("Your choice: ");

        String choice = scanner.nextLine().trim();
        TransactionType type;

        switch (choice) {
            case "1":
                type = TransactionType.INCOME;
                break;
            case "2":
                type = TransactionType.EXPENSE;
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        List<Transaction> filtered = TransactionManager.getTransactionsByType(type);
        displayTransactionsWithSummary(filtered, type.toString());
    }

    public void displayBalance() {
        double balance = TransactionManager.calculateBalance();
        System.out.println("\n○-○-○-○-○-○ Current Balance: ○-○-○-○-○-○");
        System.out.printf("Balance: %.2f kr\n", balance);
        System.out.println("○-○-○-○-○-○-○-○-○-○-○-○-○-○-○-○-○-○-○-○-○");
    }

    public void addTransaction() {
        try {
            System.out.println("\n~~~~~~~~~ Add a transaction ~~~~~~~~~");

            System.out.println("Select type of transaction:");
            System.out.println("1. Income");
            System.out.println("2. Expense");
            System.out.print("Your choice: ");
            String typeChoice = scanner.nextLine().trim();

            TransactionType type;
            if (typeChoice.equals("1")) {
                type = TransactionType.INCOME;
            } else if (typeChoice.equals("2")) {
                type = TransactionType.EXPENSE;
            } else {
                System.out.println("Invalid choice!");
                return;
            }

            System.out.print("\nEnter amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter description: ");
            String description = scanner.nextLine().trim();

            System.out.print("Enter date (yyyy-MM-dd) or press 'enter' today's date: ");
            String dateInput = scanner.nextLine().trim();
            LocalDate date;
            if (dateInput.isEmpty()) {
                date = LocalDate.now();
            } else {
                date = LocalDate.parse(dateInput);
            }

            Transaction transaction = new Transaction(amount, description, date, type);
            TransactionManager.addTransaction(transaction);

            System.out.println("\nThe transaction has been added successfully!");
            System.out.println(transaction);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect amount!");
        } catch (DateTimeParseException e) {
            System.out.println("Incorrect date format!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeTransaction() {
        if (TransactionManager.isEmpty()) {
            System.out.println("\nThere are no transactions to remove!");
            return;
        }

        displayAllTransactions();

        System.out.print("\nEnter the ID of the transaction you want to remove: ");
        String idInput = scanner.nextLine().trim();

        try {
            UUID id = UUID.fromString(idInput);
            Transaction toRemove = TransactionManager.getTransactionById(id);

            if (toRemove != null) {
                System.out.println("\nYou are about to remove:");
                System.out.println(toRemove);
                System.out.print("Are you sure? (yes/no): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equals("yes") || confirmation.equals("y")) {
                    TransactionManager.removeTransaction(id);
                    System.out.println("The transaction has been removed successfully!");
                } else {
                    System.out.println("Canceling the removal of this transaction!");
                }
            } else {
                System.out.println("No transaction with that ID was found!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect ID-format! Please write or copy the exact ID.");
        }
    }
}
