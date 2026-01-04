package com.example.personalfinancedb.command;

import com.example.personalfinancedb.ui.TransactionUI;

public class DisplayBalanceCommand implements Command {
    private final TransactionUI transactionUI;

    public DisplayBalanceCommand(TransactionUI transactionUI) {
        this.transactionUI = transactionUI;
    }

    @Override
    public void execute() {
        transactionUI.displayBalance();
    }
}
