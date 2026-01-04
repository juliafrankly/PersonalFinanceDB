package com.example.personalfinancedb.model;

public enum TransactionType {
    INCOME("Income"),
    EXPENSE("Expense"),;

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static TransactionType fromString(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }

        for (TransactionType type : values()) {
            if (type.displayName.equalsIgnoreCase(input) || type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid transaction type: " + input);
    }
}