package com.example.personalfinancedb.ui;

import com.example.personalfinancedb.database.DatabaseConfig;
import com.example.personalfinancedb.menu.MainMenu;
import com.example.personalfinancedb.service.TransactionManager;

import java.util.Scanner;

public class EconomyApp {
    private final Scanner scanner;
    private final TransactionUI transactionUI;

    public EconomyApp() {
        this.scanner = new Scanner(System.in);
        this.transactionUI = new TransactionUI(scanner);
    }

    public void start() {
        displayWelcome();

        System.out.println("\nChecking the database connection..");
        DatabaseConfig.testConnection();
        TransactionManager.initializeDatabase();

        MainMenu mainMenu = new MainMenu(scanner, transactionUI);
        mainMenu.show();

        displayGoodbye();
        scanner.close();
    }

    private void displayWelcome() {
        System.out.println("=====================================");
        System.out.println("     Welcome to your economy app!    ");
        System.out.println("=====================================");
    }

    private void displayGoodbye() {
        System.out.println("\nThank you for using the program!");
    }
}