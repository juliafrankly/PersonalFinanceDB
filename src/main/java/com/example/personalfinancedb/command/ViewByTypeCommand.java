package com.example.personalfinancedb.command;

import com.example.personalfinancedb.ui.TransactionUI;

public class ViewByTypeCommand implements Command {
    private final TransactionUI transactionUI;

    public ViewByTypeCommand(TransactionUI transactionUI) {
        this.transactionUI = transactionUI;
    }

    @Override
    public void execute() {
        transactionUI.viewByType();
    }
}
