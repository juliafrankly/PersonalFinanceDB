package com.example.personalfinancedb.menu;

import com.example.personalfinancedb.command.*;
import com.example.personalfinancedb.ui.TransactionUI;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainMenu extends AbstractMenu {
    private final TransactionUI transactionUI;

    public MainMenu(Scanner scanner, TransactionUI transactionUI) {
        super(scanner);
        this.transactionUI = transactionUI;
    }

    @Override
    protected Map<String, Command> createCommands() {
        Map<String, Command> map = new HashMap<>();
        map.put("1", new AddTransactionCommand(transactionUI));
        map.put("2", new RemoveTransactionCommand(transactionUI));
        map.put("3", new DisplayAllTransactionsCommand(transactionUI));
        map.put("4", new ShowSubMenuCommand(new PeriodMenu(scanner, transactionUI)));
        map.put("5", new ViewByTypeCommand(transactionUI));
        map.put("6", new DisplayBalanceCommand(transactionUI));
        map.put("0", new ExitCommand());
        return map;
    }

    @Override
    protected void displayMenu() {
        System.out.println("\n~~~~~~~~~~ MAIN MENU ~~~~~~~~~~");
        System.out.println("1. Add transaction");
        System.out.println("2. Remove transaction");
        System.out.println("3. Show all transactions");
        System.out.println("4. Show transaction by period");
        System.out.println("5. Show transaction by type");
        System.out.println("6. Show current balance");
        System.out.println("0. Exit");
        System.out.print("\nYour choice: ");
    }
}