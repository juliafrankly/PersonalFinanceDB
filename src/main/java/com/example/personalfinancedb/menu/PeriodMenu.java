package com.example.personalfinancedb.menu;

import com.example.personalfinancedb.command.*;
import com.example.personalfinancedb.ui.TransactionUI;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PeriodMenu extends AbstractMenu {
    private final TransactionUI transactionUI;

    public PeriodMenu(Scanner scanner, TransactionUI transactionUI) {
        super(scanner);
        this.transactionUI = transactionUI;
    }

    @Override
    protected Map<String, Command> createCommands() {
        Map<String, Command> map = new HashMap<>();
        map.put("1", new ViewByDayCommand(transactionUI));
        map.put("2", new ViewByWeekCommand(transactionUI));
        map.put("3", new ViewByMonthCommand(transactionUI));
        map.put("4", new ViewByYearCommand(transactionUI));
        map.put("0", new ExitCommand());
        return map;
    }

    @Override
    protected void displayMenu() {
        System.out.println("\n~~~~~~~~~~ Show transaction by period ~~~~~~~~~~");
        System.out.println("1. Day");
        System.out.println("2. Week");
        System.out.println("3. Month");
        System.out.println("4. Year");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
    }
}