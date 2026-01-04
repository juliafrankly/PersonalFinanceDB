package com.example.personalfinancedb.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private double amount;
    private LocalDate date;
    private String description;
    private TransactionType type;
    private UUID id;

    public Transaction(double amount, String description, LocalDate date, TransactionType type) {
        this(amount, description, date, type, UUID.randomUUID());
    }

    public Transaction(double amount, String description, LocalDate date, TransactionType type, UUID id) {
        validateAmount(amount);
        validateDate(date);
        validatedescription(description);

        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.id = id;
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the future");
        }
    }

    private void validatedescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter a description");
        }
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getdescription() {
        return description;
    }

    public TransactionType getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public void setAmount(double amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    public void setdescription(String description) {
        validatedescription(description);
        this.description = description;
    }

    public void setType(TransactionType type) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        this.type = type;
    }

    public double getSignedAmount() {
        return type == TransactionType.INCOME ? amount : -amount;
    }

    @Override
    public String toString() {
        return type + " | " +
                "Amount: " + amount + " kr | " +
                "Description: " + description + " | " +
                "Date: " + date.format(DATE_FORMAT) + " | " +
                "ID: " + id;
    }
}
