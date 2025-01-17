package org.CashCatalysts.CashCatalysts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainWindowController {
    @FXML
    private Pane main_root;
    @FXML
    private Pane main_pane;
    @FXML
    private Pane nav_menu;

    public void initialize() throws IOException {
        nav_menu.setVisible(false);
        loadTransactions();
    }

    public void toggleMenu(ActionEvent ignore) {
        nav_menu.setVisible(!nav_menu.isVisible());
    }

    private void loadTransactions() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/Transactions.fxml"));
        loader.setController(new TransactionsController());
        main_pane.getChildren().add(loader.load());
    }
}
