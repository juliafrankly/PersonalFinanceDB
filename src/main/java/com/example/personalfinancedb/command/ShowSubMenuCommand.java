package com.example.personalfinancedb.command;

import com.example.personalfinancedb.menu.AbstractMenu;

public class ShowSubMenuCommand implements Command {
    private final AbstractMenu subMenu;

    public ShowSubMenuCommand(AbstractMenu subMenu) {
        this.subMenu = subMenu;
    }

    @Override
    public void execute() {
        subMenu.show();
    }
}