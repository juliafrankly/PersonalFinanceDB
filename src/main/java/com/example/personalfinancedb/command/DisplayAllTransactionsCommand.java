package com.example.personalfinancedb.command;

import com.example.personalfinancedb.ui.TransactionUI;

public class DisplayAllTransactionsCommand implements Command {
    private final TransactionUI transactionUI;

    public DisplayAllTransactionsCommand(TransactionUI transactionUI) {
        this.transactionUI = transactionUI;
    }

    @Override
    public void execute() {
        transactionUI.displayAllTransactions();
    }
}