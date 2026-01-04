package com.example.personalfinancedb.menu;

import com.example.personalfinancedb.command.Command;

import java.util.Map;
import java.util.Scanner;

public abstract class AbstractMenu {
    protected Scanner scanner;
    protected Map<String, Command> commands;

    public AbstractMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    protected abstract Map<String, Command> createCommands();
    protected abstract void displayMenu();

    public void show() {
        if (commands == null) {
            commands = createCommands();
        }

        boolean running = true;
        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            Command command = commands.get(choice);
            if (command != null) {
                command.execute();
                if (choice.equals("0")) {
                    running = false;
                }
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }
}