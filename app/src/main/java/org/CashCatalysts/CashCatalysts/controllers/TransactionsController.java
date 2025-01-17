package org.CashCatalysts.CashCatalysts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.CashCatalysts.CashCatalysts.Transactions.Transaction;
import org.CashCatalysts.CashCatalysts.Transactions.TransactionHandler;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TransactionsController {
    @FXML
    private VBox transactionCards;
    @FXML
    private BorderPane transactions_root;

    public void initialize() throws IOException {
        loadNavbar();

        List<Transaction> transactionsTest = new ArrayList<>();
        transactionsTest.add(TransactionHandler.createTransaction("Test1", "potato", Date.valueOf("2025-01-16"), 100, 10));
        transactionsTest.add(TransactionHandler.createTransaction("TestPop", "banana", Date.valueOf("2025-01-15"), 200, 0));


        try {
            loadTransactions(transactionsTest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNavbar() throws IOException {
        FXMLLoader navbarLoader = new FXMLLoader(getClass().getResource("../forms/Navbar.fxml"));
        NavbarController navbarController = new NavbarController();
        navbarLoader.setController(navbarController);
        transactions_root.setLeft(navbarLoader.load());
    }

    private void loadTransactions(List<Transaction> transactions) throws IOException {
        for (Transaction transaction : transactions) {
            FXMLLoader transactionCardLoader = new FXMLLoader(getClass().getResource("../forms/TransactionCard.fxml"));
            transactionCardLoader.setController(new TransactionCardController(transaction, this::deleteTransaction));

            transactionCards.getChildren().add(transactionCardLoader.load());
        }
    }

    public void deleteTransaction(Transaction transaction) {
        System.out.println(transaction);
    }
}
