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
        loadPage("../forms/Dashboard.fxml");
    }

    public void toggleMenu(ActionEvent ignore) {
        nav_menu.setVisible(!nav_menu.isVisible());
    }

    private void loadTransactions() throws IOException {
        main_pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/Transactions.fxml"));
        loader.setController(new TransactionsController());
        main_pane.getChildren().add(loader.load());
    }

    private void loadPage(String path) throws IOException {
        main_pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        main_pane.getChildren().add(loader.load());
    }

    private void loadPage(String path, Object controller) throws IOException {
        main_pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setController(controller);
        main_pane.getChildren().add(loader.load());
    }

    public void onTransactionsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Transactions.fxml", new TransactionsController());
    }

    public void onDashboardClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Dashboard.fxml");
    }

    public void onAccountsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/AccountManagement.fxml");
    }
}
