package com.example.personalfinancedb.command;

import com.example.personalfinancedb.ui.TransactionUI;

public class ViewByDayCommand implements Command {
    private final TransactionUI transactionUI;

    public ViewByDayCommand(TransactionUI transactionUI) {
        this.transactionUI = transactionUI;
    }

    @Override
    public void execute() {
        transactionUI.viewByDay();
    }
}