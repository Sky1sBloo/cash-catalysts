package org.CashCatalysts.CashCatalysts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;
import org.CashCatalysts.CashCatalysts.budgets.BudgetHandler;

import java.io.IOException;

public class MainWindowController {
    private final TransactionHandler transactionHandler;
    private final BudgetHandler budgetHandler;

    @FXML
    private Pane main_root;
    @FXML
    private Pane main_pane;
    @FXML
    private Pane nav_menu;

    public MainWindowController(TransactionHandler transactionHandler, BudgetHandler budgetHandler) {
        this.transactionHandler = transactionHandler;
        this.budgetHandler = budgetHandler;
    }

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
        loader.setController(new TransactionsController(transactionHandler, budgetHandler));
        main_pane.getChildren().add(loader.load());
    }

    private void loadPage(String path) throws IOException {
        nav_menu.setVisible(false);
        main_pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        main_pane.getChildren().add(loader.load());
    }

    private void loadPage(String path, Object controller) throws IOException {
        nav_menu.setVisible(false);
        main_pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setController(controller);
        main_pane.getChildren().add(loader.load());
    }


    public void onTransactionsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Transactions.fxml", new TransactionsController(transactionHandler, budgetHandler));
    }

    public void onDashboardClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Dashboard.fxml");
    }

    public void onAccountsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/AccountManagement.fxml");
    }

    public void onSubscriptionsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Subscriptions.fxml");
    }

    public void onAnalyticsClick(ActionEvent ignore) throws IOException {
        loadPage("../forms/Analytics.fxml");
    }
}
