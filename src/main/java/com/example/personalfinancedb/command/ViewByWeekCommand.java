package com.example.personalfinancedb.command;

import com.example.personalfinancedb.ui.TransactionUI;

public class ViewByWeekCommand implements Command {
    private final TransactionUI transactionUI;

    public ViewByWeekCommand(TransactionUI transactionUI) {
        this.transactionUI = transactionUI;
    }

    @Override
    public void execute() {
        transactionUI.viewByWeek();
    }
}