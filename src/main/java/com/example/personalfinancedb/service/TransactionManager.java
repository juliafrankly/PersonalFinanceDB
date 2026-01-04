package com.example.personalfinancedb.service;

import com.example.personalfinancedb.database.TransactionDAO;
import com.example.personalfinancedb.model.Transaction;
import com.example.personalfinancedb.model.TransactionType;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TransactionManager {
    private static final TransactionDAO dao = new TransactionDAO();

    public static void initializeDatabase() {
        dao.createTable();
    }

    public static void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("The transaction cannot be null");
        }
        try {
            dao.addTransaction(transaction);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding transaction: " + e.getMessage(), e);
        }
    }

    public static boolean removeTransaction(UUID id) {
        try {
            return dao.removeTransaction(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error removing transaction: " + e.getMessage(), e);
        }
    }

    public static List<Transaction> getAllTransactions() {
        try {
            return dao.getAllTransactions();
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static boolean isEmpty() {
        try {
            return dao.isEmpty();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return true;
        }
    }

    public static Transaction getTransactionById(UUID id) {
        try {
            return dao.getTransactionById(id);
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
            return null;
        }
    }

    public static double calculateBalance() {
        try {
            return dao.calculateBalance();
        } catch (SQLException e) {
            System.err.println("Error when calculating current balance: " + e.getMessage());
            return 0.0;
        }
    }

    public static List<Transaction> getTransactionsByDate(LocalDate date) {
        try {
            return dao.getTransactionsByDate(date);
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<Transaction> getTransactionsByWeek(LocalDate startDate, LocalDate endDate) {
        try {
            return dao.getTransactionsByWeek(startDate, endDate);
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<Transaction> getTransactionsByMonth(int year, int month) {
        try {
            return dao.getTransactionsByMonth(year, month);
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<Transaction> getTransactionsByYear(int year) {
        try {
            return dao.getTransactionsByYear(year);
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<Transaction> getTransactionsByType(TransactionType type) {
        try {
            return dao.getTransactionsByType(type);
        } catch (SQLException e) {
            System.err.println("\n" + "Error retrieving transactions: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static TransactionSummary calculateSummary(List<Transaction> transactionList) {
        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : transactionList) {
            if (t.getType() == TransactionType.INCOME) {
                totalIncome += t.getAmount();
            } else {
                totalExpense += t.getAmount();
            }
        }

        return new TransactionSummary(totalIncome, totalExpense);
    }

    public static class TransactionSummary {
        private final double totalIncome;
        private final double totalExpense;

        public TransactionSummary(double totalIncome, double totalExpense) {
            this.totalIncome = totalIncome;
            this.totalExpense = totalExpense;
        }

        public double getTotalIncome() {
            return totalIncome;
        }

        public double getTotalExpense() {
            return totalExpense;
        }

        public double getNet() {
            return totalIncome - totalExpense;
        }

        @Override
        public String toString() {
            return String.format("Total income: %.2f kr\nTotal expense: %.2f kr\nNet: %.2f kr",
                    totalIncome, totalExpense, getNet());
        }
}
}