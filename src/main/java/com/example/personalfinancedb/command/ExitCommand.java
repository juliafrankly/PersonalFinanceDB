package com.example.personalfinancedb.command;

public class ExitCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Exiting...");
    }
}
