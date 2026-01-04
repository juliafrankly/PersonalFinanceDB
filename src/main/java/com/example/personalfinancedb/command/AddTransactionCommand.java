package com.example.personalfinancedb.command;

import com.example.personalfinancedb.ui.TransactionUI;

public class AddTransactionCommand implements Command {
    private final TransactionUI transactionUI;

    public AddTransactionCommand(TransactionUI transactionUI) {
        this.transactionUI = transactionUI;
    }

    @Override
    public void execute() {
        transactionUI.addTransaction();
    }
}