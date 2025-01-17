package org.CashCatalysts.CashCatalysts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;


public class NavbarController {
    @FXML
    private GridPane nav_menu;

    public void initialize() {
        nav_menu.setVisible(false);
    }

    public void toggleMenu(ActionEvent ignore) {
        nav_menu.setVisible(!nav_menu.isVisible());
    }
}
