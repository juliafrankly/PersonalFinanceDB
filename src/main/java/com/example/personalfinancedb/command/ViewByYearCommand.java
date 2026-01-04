package com.example.personalfinancedb.command;

import com.example.personalfinancedb.ui.TransactionUI;

public class ViewByYearCommand implements Command {
    private final TransactionUI transactionUI;

    public ViewByYearCommand(TransactionUI transactionUI) {
        this.transactionUI = transactionUI;
    }

    @Override
    public void execute() {
        transactionUI.viewByYear();
    }
}
