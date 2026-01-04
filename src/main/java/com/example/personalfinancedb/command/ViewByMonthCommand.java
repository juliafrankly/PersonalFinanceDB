package com.example.personalfinancedb.command;

import com.example.personalfinancedb.ui.TransactionUI;

public class ViewByMonthCommand implements Command {
    private final TransactionUI transactionUI;

    public ViewByMonthCommand(TransactionUI transactionUI) {
        this.transactionUI = transactionUI;
    }

    @Override
    public void execute() {
        transactionUI.viewByMonth();
    }
}
